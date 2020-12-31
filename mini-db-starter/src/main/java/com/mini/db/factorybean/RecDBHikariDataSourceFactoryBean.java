package com.mini.db.factorybean;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author songjiuhua
 * Created by 2020/3/18 18:59
 */
public class RecDBHikariDataSourceFactoryBean implements FactoryBean<Object> {

    private HikariDataSource dataSource;

    @Override
    public Object getObject() throws Exception {
        return dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return HikariDataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
