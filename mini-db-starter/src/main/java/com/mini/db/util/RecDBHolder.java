package com.mini.db.util;

import java.util.concurrent.ConcurrentHashMap;
/**
 * @author songjiuhua
 * Created by 2020/3/19 15:23
 */
public class RecDBHolder {

    /**
     * 数据源bean相关 key:数据库名称（数据库库名 + master/slave）  value：当前数据库名称相关的bean名称信息
     */
    private static final ConcurrentHashMap<String, RecDBInfoBean> dbInfoBeanCache = new ConcurrentHashMap<>();

    public static void addDbInfoBeanCache(String dbName, RecDBInfoBean recDBInfoBean){
        dbInfoBeanCache.put(dbName, recDBInfoBean);
    }

    public static ConcurrentHashMap<String, RecDBInfoBean> getDbInfoBeanCache(){
        return dbInfoBeanCache;
    }

}
