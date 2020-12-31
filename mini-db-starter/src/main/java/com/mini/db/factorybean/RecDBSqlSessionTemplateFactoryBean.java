package com.mini.db.factorybean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecDBSqlSessionTemplateFactoryBean implements FactoryBean<Object> {

    private DataSource dataSource;

    private List<String> resources;


    @Override
    public Object getObject() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        List<Resource> resourceList = new ArrayList<>();
        Set<String> resouceFileNameSet = new HashSet<>();
        for(String resource : resources){
            Resource[] resList = new PathMatchingResourcePatternResolver().getResources(resource);
            for(Resource res : resList){
                if(!resouceFileNameSet.contains(res.getFilename())){
                    resouceFileNameSet.add(res.getFilename());
                    resourceList.add(res);
                }
            }
        }
        Resource[] resourceArray = new Resource[resourceList.size()];
        for(int i=0; i < resourceList.size(); ++i){
            resourceArray[i] = resourceList.get(i);
        }
        bean.setMapperLocations(resourceArray);
        SqlSessionFactory factory =  bean.getObject();
        //下划线转驼峰
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(factory);
        sqlSessionTemplate.getConnection();
        return sqlSessionTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionTemplate.class;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
