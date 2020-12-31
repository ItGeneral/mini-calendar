package com.mini.db.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mapper 代理工厂
 * @author songjiuhua
 * Created by 2020/3/18 13:49
 */
public class MapperMethodProxyFactory<T> implements FactoryBean<T>, ApplicationContextAware {

    /** 接口类 */
    private Class<T> mapperInterface;
    /**key为接口方法 value为sqlSession的beanName */
    private ConcurrentHashMap<Method, String> methodSqlSessionCache;
    /** 上下文 */
    private ApplicationContext applicationContext;

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public void setMethodSqlSessionCache(ConcurrentHashMap<Method, String> methodSqlSessionCache) {
        this.methodSqlSessionCache = methodSqlSessionCache;
    }

    @Override
    public T getObject() throws Exception {
        return (T) new MapperMethodProxy(methodSqlSessionCache, mapperInterface, applicationContext).newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
