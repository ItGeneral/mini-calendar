package com.mini.db.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/3/18 14:35
 */
public class RecDBInfoBean {

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 值为master或slave
     */
    private String masterOrSlave;

    /**
     * 唯一键(数据库库名 + master/slave)
     */
    private String newDbName;

    /**
     * 数据源bean名称
     * 主库数据源bean名称 = 数据库名称 + "-dataSource-master"
     * 从库数据源bean名称 = 数据库名称 + "-dataSource-slave"
     */
    private String dataSourceBeanName;
    /**
     * 事务管理器bean名称
     * 主库事务管理器bean名称 = 数据库名称 + "-sqlTransactionManager-master"
     * 从库事务管理器bean名称 = 数据库名称 + "-sqlTransactionManager-slave"
     */
    private String sqlTransactionManagerBeanName;
    /**
     * sqlSession bean 名称
     * 主库sqlSession bean名称 = 数据库名称 + "-sqlSessionTemplate-master"
     * 从库sqlSession bean名称 = 数据库名称 + "-sqlSessionTemplate-slave"
     */
    private String sqlSessionTemplateBeanName;
    /**
     * mapper.xml资源
     */
    private List<String> resources = new ArrayList<>();

    public List<String> getResources() {
        return resources;
    }

    public void addResource(String resource) {
        this.resources.add(resource);
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getMasterOrSlave() {
        return masterOrSlave;
    }

    public void setMasterOrSlave(String masterOrSlave) {
        this.masterOrSlave = masterOrSlave;
    }

    public String getNewDbName() {
        return newDbName;
    }

    public void setNewDbName(String newDbName) {
        this.newDbName = newDbName;
    }

    public String getDataSourceBeanName() {
        return dataSourceBeanName;
    }

    public void setDataSourceBeanName(String dataSourceBeanName) {
        this.dataSourceBeanName = dataSourceBeanName;
    }

    public String getSqlTransactionManagerBeanName() {
        return sqlTransactionManagerBeanName;
    }

    public void setSqlTransactionManagerBeanName(String sqlTransactionManagerBeanName) {
        this.sqlTransactionManagerBeanName = sqlTransactionManagerBeanName;
    }

    public String getSqlSessionTemplateBeanName() {
        return sqlSessionTemplateBeanName;
    }

    public void setSqlSessionTemplateBeanName(String sqlSessionTemplateBeanName) {
        this.sqlSessionTemplateBeanName = sqlSessionTemplateBeanName;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
