package com.mini.db.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public class RecDBTransactionManagerFactoryBean implements FactoryBean<Object> {

    private DataSource dataSource;
    @Override
    public Object getObject() throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public Class<?> getObjectType() {
        return DataSourceTransactionManager.class;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
