package com.mini.db.proxy;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.lang.UsesJava7;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mapper代理类
 * @author songjiuhua
 * Created by 2020/3/18 13:49
 */
public class MapperMethodProxy<T> implements InvocationHandler {

    /** 应用上下文 */
    private ApplicationContext applicationContext;
    /** 接口类 */
    private final Class<T> mapperInterface;
    /** MapperMethod缓存 */
    private final Map<Method, MapperMethod> methodCache = new HashMap<>();
    /**key为接口方法 value为sqlSession的beanName */
    private final ConcurrentHashMap<Method, String> methodSqlSessionCache ;

    public MapperMethodProxy(ConcurrentHashMap<Method, String> methodSqlSessionCache, Class<T> mapperInterface, ApplicationContext applicationContext) {
        this.methodSqlSessionCache = methodSqlSessionCache;
        this.mapperInterface = mapperInterface;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            } else if (isDefaultMethod(method)) {
                return invokeDefaultMethod(proxy, method, args);
            }
        } catch (Throwable t) {
            throw ExceptionUtil.unwrapThrowable(t);
        }
        MapperMethod mapperMethod = cachedMapperMethod(method);
        String sqlSessionName = this.methodSqlSessionCache.get(method);
        SqlSession sqlSession = (SqlSession) applicationContext.getBean(sqlSessionName);
        return mapperMethod.execute(sqlSession, args);
    }

    @SuppressWarnings("unchecked")
    public T newInstance() {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, this);
    }

    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            String sqlSessionName = this.methodSqlSessionCache.get(method);
            SqlSession sqlSession = (SqlSession) applicationContext.getBean(sqlSessionName);
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }

    @UsesJava7
    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        return constructor
                .newInstance(declaringClass,
                        MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
                                | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC)
                .unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
    }

    /**
     * Backport of java.lang.reflect.Method#isDefault()
     */
    private boolean isDefaultMethod(Method method) {
        return (method.getModifiers()
                & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC
                && method.getDeclaringClass().isInterface();
    }

}
