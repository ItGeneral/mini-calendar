package com.mini.db.handler;

import com.mini.db.annotation.EnableRecDB;
import com.mini.db.annotation.RecDBReadable;
import com.mini.db.annotation.RecDBWritable;
import com.mini.db.config.CustomDataSourceConfig;
import com.mini.db.config.DbConfig;
import com.mini.db.factorybean.RecDBHikariDataSourceFactoryBean;
import com.mini.db.factorybean.RecDBSqlSessionTemplateFactoryBean;
import com.mini.db.factorybean.RecDBTransactionManagerFactoryBean;
import com.mini.db.annotation.RecDB;
import com.mini.db.proxy.MapperMethodProxyFactory;
import com.mini.db.util.RecDBInfoBean;
import com.mini.db.util.RecDBBeanBinder;
import com.mini.db.util.RecDBHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class RecDBRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(RecDBRegistrar.class);

    private ConfigurableEnvironment environment;

    static final String DEFAULT_RESOURCE_PATTERN = "/**/*.class";


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerMybatisClients(importingClassMetadata, registry);
    }

    /**
     * 1、获取@EnableRecDB注解上的扫描包路径
     * 2、获取所有实现CustomDataSourceConfig接口的bean，得到接口configDataSource方法返回的结果dbConfigList，扫描出Mapper接口注册到Spring容器中
     * 3、根据扫描包路径，扫描出所有包含@RecDB注解的类
     * 4、解析@RecDB注解的类，并遍历类中每个方法，判断是否同时使用了主库和从库，如果同时使用了主库和从库，则每个数据库需要分别注册主库和从库对应的数据源
     * 5、注册所有包含@RecDB注解的类到Spring容器中
     * 6、注册所有dataSource数据源
     * 7、注册所有sqlSessionTemplate
     * 8、注册所有sqlTransactionManager事务管理器
     * @param metadata 注解元数据
     * @param registry 注册器
     */
    public void registerMybatisClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
        List<DbConfig> dbConfigList = findConfigDataSource(beanFactory);
        if (!CollectionUtils.isEmpty(dbConfigList)){
            for (DbConfig dbConfig : dbConfigList) {
                Set<Class<?>> classes = this.scanBasePackageExcludeAnnotationRecDB(dbConfig.getScanBasePackage());
                for (Class<?> clazz : classes) {
                    //method与sqlSessionTemplate bean名称的映射关系
                    ConcurrentHashMap<Method, String> methodSqlSessionCache = new ConcurrentHashMap<>();
                    //找出有多少数据源
                    String mapperPath = dbConfig.getMapperPath();
                    mapperPath = StringUtils.isEmpty(mapperPath) ? "classpath*:mapper/**/*.xml" : mapperPath;
                    this.buildRecDBHolder(methodSqlSessionCache, clazz, dbConfig.getDbSourceName(), mapperPath);
                    //注册所有扫描出来的mapper
                    this.registerRecDBInstance(methodSqlSessionCache, clazz, registry);
                }
            }
        }
        MergedAnnotations annotations = metadata.getAnnotations();
        MergedAnnotation<EnableRecDB> mergedAnnotation = annotations.get(EnableRecDB.class);
        Map<String, Object> stringObjectMap = mergedAnnotation.asMap();
        String[] basePackage = (String[]) stringObjectMap.get("basePackages");
        if (basePackage.length == 0){
            String defaultBasePackage = ((StandardAnnotationMetadata) metadata).getIntrospectedClass().getPackage().getName();
            basePackage = new String[]{defaultBasePackage};
            log.warn("can not found field [basePackages] value in @EnableRecDB, the default mapper scan path is  [{}]", defaultBasePackage);
        }
        Set<Class<?>> classes = this.scanBasePackageWithAnnotatedRecDB(basePackage);
        for (Class<?> clazz : classes) {
            RecDB recDB = clazz.getAnnotation(RecDB.class);
            String recDBName = recDB.name();
            Assert.isTrue(!StringUtils.isEmpty(recDBName), "name in @RecDB from '"+ clazz.getCanonicalName() + "' must be provided");
            String resource = recDB.resource();
            Assert.isTrue(!StringUtils.isEmpty(resource), "resource in @RecDB from '"+ clazz.getCanonicalName() + "' must be provided");
            //method与sqlSessionTemplate bean名称的映射关系
            ConcurrentHashMap<Method, String> methodSqlSessionCache = new ConcurrentHashMap<>();
            //找出有多少数据源
            this.buildRecDBHolder(methodSqlSessionCache, clazz, recDBName, resource);
            //注册所有扫描出来的mapper
            this.registerRecDBInstance(methodSqlSessionCache, clazz, registry);
        }


        Iterable<ConfigurationPropertySource> propertySources = ConfigurationPropertySources.get(environment);
        Set<String> transactionSet = new HashSet<>();
        for (Map.Entry<String, RecDBInfoBean> entry : RecDBHolder.getDbInfoBeanCache().entrySet()){
            RecDBInfoBean recDbAnnotationInfo = entry.getValue();
            transactionSet.add(recDbAnnotationInfo.getSqlTransactionManagerBeanName());
            //注册数据源dataSource
            registerDataSource(registry, recDbAnnotationInfo, propertySources);
            //注册sqlSession
            processSqlSession(registry, recDbAnnotationInfo.getResources(), recDbAnnotationInfo.getDataSourceBeanName(), recDbAnnotationInfo.getSqlSessionTemplateBeanName());
            //注册事务管理器TransactionManager
            processTransactionManager(registry, recDbAnnotationInfo.getDataSourceBeanName(), recDbAnnotationInfo.getSqlTransactionManagerBeanName());
        }
        log.info("database transactionManager name is: {}", transactionSet.toString());
        transactionSet.clear();
    }

    /**
     * 根据RecDBWritable注解，遍历出哪些数据库分主库与从库
     * 缓存每个方法对应的主库或从库
     * @param methodSqlSessionCache method与sqlSessionTemplate bean名称的映射关系
     * @param clazz 实体类
     * @param recDBName @RecDB注解name属性值
     * @param recDBSource mapper.xml资源
     */
    private void buildRecDBHolder(ConcurrentHashMap<Method, String> methodSqlSessionCache, Class<?> clazz, String recDBName, String recDBSource){
        boolean classMaster ;
        //只要类上添加了RecDBWritable注解，就表示走主库
        if (clazz.isAnnotationPresent(RecDBWritable.class)){
            classMaster = true;
        }else {
            classMaster = false;
        }
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods){
            String masterOrSlave;
            if (method.isAnnotationPresent(RecDBWritable.class)){
                masterOrSlave = "master";
            }else if (method.isAnnotationPresent(RecDBReadable.class)){
                masterOrSlave = "slave";
            }else{
                masterOrSlave = classMaster ? "master" : "slave";
            }
            String dbMasterOrSlaveName = recDBName + "-" + masterOrSlave;
            String dataSourceBeanName = recDBName + "-dataSource-" + masterOrSlave;
            String sqlTransactionManagerBeanName = recDBName + "-sqlTransactionManager-" + masterOrSlave;
            String sqlSessionTemplateBeanName = recDBName + "-sqlSessionTemplate-" + masterOrSlave;
            RecDBInfoBean recDBInfoBean = RecDBHolder.getDbInfoBeanCache().get(dbMasterOrSlaveName);
            if (recDBInfoBean == null){
                recDBInfoBean = new RecDBInfoBean();
                recDBInfoBean.setDbName(recDBName);
                recDBInfoBean.setNewDbName(dbMasterOrSlaveName);
                recDBInfoBean.setMasterOrSlave(masterOrSlave);
                recDBInfoBean.addResource(recDBSource);
                recDBInfoBean.setDataSourceBeanName(dataSourceBeanName);
                recDBInfoBean.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
                recDBInfoBean.setSqlTransactionManagerBeanName(sqlTransactionManagerBeanName);
                RecDBHolder.addDbInfoBeanCache(dbMasterOrSlaveName, recDBInfoBean);
            }
            methodSqlSessionCache.put(method, sqlSessionTemplateBeanName);
        }
    }

    /**
     * 注册所有包含@RecDB注解的类到Spring容器中
     * 并为每个类设置动态代理 MapperMethodProxyFactory
     * @param methodSqlSessionCache method与sqlSessionTemplate bean名称的映射关系
     * @param clazz 实体类
     * @param registry 注册器
     */
    private void registerRecDBInstance(ConcurrentHashMap<Method, String> methodSqlSessionCache, Class<?> clazz, BeanDefinitionRegistry registry){
        String beanName = Introspector.decapitalize(clazz.getSimpleName());
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("mapperInterface", clazz.getName());
        definition.setBeanClass(MapperMethodProxyFactory.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.setRole(RootBeanDefinition.ROLE_INFRASTRUCTURE);
        definition.getPropertyValues().add("methodSqlSessionCache", methodSqlSessionCache);
        registry.registerBeanDefinition(beanName, definition);
    }

    /**
     * 注册数据源dataSource
     * @param recDBInfoBean db信息缓存类
     * @param propertySources 环境变量
     * @param registry 注册器
     */
    private void registerDataSource(BeanDefinitionRegistry registry, RecDBInfoBean recDBInfoBean, Iterable<ConfigurationPropertySource> propertySources){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RecDBHikariDataSourceFactoryBean.class);
        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanDefinitionBuilder.getRawBeanDefinition();

        Bindable target = Bindable.of(HikariDataSource.class);
        RecDBBeanBinder.Bean<?> bean = RecDBBeanBinder.Bean.get(target, false);
        RecDBBeanBinder.BeanSupplier beanSupplier = bean.getSupplier(target);
        Collection<RecDBBeanBinder.BeanProperty> beanPropertyCollection = bean.getProperties().values();

        String dataSourcePrefix = "rec.db.".concat(recDBInfoBean.getDbName()).concat(".").concat(recDBInfoBean.getMasterOrSlave());
        ConfigurationPropertyName prefix = ConfigurationPropertyName.of(dataSourcePrefix);
        boolean configuration = false;
        for (RecDBBeanBinder.BeanProperty property : beanPropertyCollection) {
            String propertyName = property.getName();
            Supplier<Object> value = property.getValue(beanSupplier);
            ConfigurationPropertyName propertyKey = prefix.append(propertyName);
            Object bound = null;
            for (ConfigurationPropertySource propertySource : propertySources) {
                ConfigurationProperty configurationProperty = propertySource.getConfigurationProperty(propertyKey);
                if (configurationProperty != null){
                    bound = configurationProperty.getValue();
                }
            }
            if (bound == null){
                continue;
            }
            configuration = true;
            if (property.isSettable()) {
                property.setValue(beanSupplier, bound);
            }else if (value == null || !bound.equals(value.get())) {
                throw new IllegalStateException("No setter found for property: " + property.getName());
            }
        }
        if (!configuration){
            throw new IllegalArgumentException("db name [" + recDBInfoBean.getDbName() + "], " +
                    "can not found it's [" + recDBInfoBean.getMasterOrSlave() + "] dataSource properties, " +
                    "please config it in application.properties. You can also use @RecDB and @RecDBWritable correctly.");
        }
        HikariDataSource dataSource = (HikariDataSource) beanSupplier.get();
        beanDefinitionBuilder.addPropertyValue("dataSource", dataSource);
        registry.registerBeanDefinition(recDBInfoBean.getDataSourceBeanName(), beanDefinition);
    }

    /**
     * 注册数据源sqlSessionTemplate
     * @param registry 注册器
     * @param resources mapper.xml资源
     * @param dataSourceBeanName dataSource的bean名称
     * @param sqlSessionTemplateBeanName sqlSessionTemplate的bean名称
     */
    private void processSqlSession(BeanDefinitionRegistry registry, List<String> resources, String dataSourceBeanName, String sqlSessionTemplateBeanName){
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(RecDBSqlSessionTemplateFactoryBean.class);
        definition.addPropertyReference("dataSource", dataSourceBeanName);
        definition.addPropertyValue("resources",resources);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.setInitMethodName("getObject");

        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, sqlSessionTemplateBeanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    /**
     * 注册数据源事务管理器
     * @param registry 注册器
     * @param dataSourceBeanName dataSource的bean名称
     * @param sqlTransactionManagerBeanName 事务管理器的bean名称
     */
    private void processTransactionManager(BeanDefinitionRegistry registry, String dataSourceBeanName, String sqlTransactionManagerBeanName){
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(RecDBTransactionManagerFactoryBean.class);
        definition.addPropertyReference("dataSource",dataSourceBeanName);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, sqlTransactionManagerBeanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }


    /**
     * 根据包名扫描所有包含@RecDB注解的类
     * @param basePackages
     * @return
     */
    private Set<Class<?>> scanBasePackageWithAnnotatedRecDB(String[] basePackages){
        Set<Class<?>> classSet = new HashSet<>();
        try{
            for (String basePackage : basePackages){
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(basePackage) + DEFAULT_RESOURCE_PATTERN;
                ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                for (Resource resource : resources){
                    if (resource.isReadable()){
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        if (reader.getAnnotationMetadata().isAnnotated(RecDB.class.getCanonicalName())){
                            String className = reader.getClassMetadata().getClassName();
                            classSet.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("delegate for mapper class error while scan base package", e);
        }
        return classSet;
    }

    /**
     * 扫描指定包路径
     * @param basePackages
     * @return
     */
    private Set<Class<?>> scanBasePackageExcludeAnnotationRecDB(String[] basePackages){
        Set<Class<?>> classSet = new HashSet<>();
        try{
            for (String basePackage : basePackages){
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(basePackage) + DEFAULT_RESOURCE_PATTERN;
                ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                for (Resource resource : resources){
                    if (resource.isReadable()){
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        if (!reader.getAnnotationMetadata().isAnnotated(RecDB.class.getCanonicalName())){
                            String className = reader.getClassMetadata().getClassName();
                            classSet.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("delegate for mapper class error while scan base package", e);
        }
        return classSet;
    }

    /**
     * 获取实现CustomDataSourceConfig类的自定义接口，并获取接口返回的DbConfig
     * @param beanFactory
     * @return
     */
    private List<DbConfig> findConfigDataSource(DefaultListableBeanFactory beanFactory){
        List<DbConfig> result = new ArrayList<>();
        try{
            String[] beanNamesForType = beanFactory.getBeanNamesForType(CustomDataSourceConfig.class);
            for (String beanName : beanNamesForType) {
                Class<?> clazz = beanFactory.getType(beanName);
                if (clazz == null){
                    continue;
                }
                Method method = clazz.getMethod("configDataSource", Environment.class);
                Object object = method.invoke(clazz.newInstance(), environment);
                List<DbConfig> dbConfigList = (List<DbConfig>) object;
                if (!CollectionUtils.isEmpty(dbConfigList)){
                    result.addAll(dbConfigList);
                    StringBuilder stringBuilder = new StringBuilder("[");
                    boolean configIllegal = false;
                    for (int i = 0; i < dbConfigList.size(); i++) {
                        DbConfig dbConfig = dbConfigList.get(i);
                        if (StringUtils.isEmpty(dbConfig.getDbSourceName()) || StringUtils.isEmpty(dbConfig.getScanBasePackage())){
                            configIllegal = true;
                        }
                        if (i == dbConfigList.size() - 1){
                            stringBuilder.append(dbConfig.toString()).append("]");
                        }else{
                            stringBuilder.append(dbConfig.toString()).append(", ");
                        }
                    }
                    if (configIllegal){
                        throw new RuntimeException("the return value of method [configDataSource] in the " + clazz.getName() + ".class, it's field [dbSourceName] " +
                                "and field [scanBasePackage] can not be null or '', the actual return value is "
                                + stringBuilder.toString());
                    }
                    log.info("find custom data source class：{}, the configuration of data source and mapper scan path is ：{}", clazz.getName(), stringBuilder.toString());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

}
