package com.mini.db.config;

import java.util.Arrays;

/**
 * @author songjiuhua
 * Created by 2020/5/8 17:54
 */
public class DbConfig {

    /**
     * 数据源名称
     */
    private String dbSourceName;
    /**
     * Mapper接口包路径
     */
    private String[] scanBasePackage;
    /**
     * Mapper.xml 扫描包路径
     */
    private String mapperPath;

    public String getDbSourceName() {
        return dbSourceName;
    }

    public void setDbSourceName(String dbSourceName) {
        this.dbSourceName = dbSourceName;
    }

    public String[] getScanBasePackage() {
        return scanBasePackage;
    }

    public void setScanBasePackage(String[] scanBasePackage) {
        this.scanBasePackage = scanBasePackage;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    @Override
    public String toString() {
        return "{" +
                "dbSourceName='" + dbSourceName + '\'' +
                ", scanBasePackage=" + Arrays.toString(scanBasePackage) +
                ", mapperPath='" + mapperPath + '\'' +
                '}';
    }
}
