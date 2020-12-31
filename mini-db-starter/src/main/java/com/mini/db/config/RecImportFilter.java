package com.mini.db.config;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RecImportFilter implements AutoConfigurationImportFilter {
    private static final Set<String> SHOULD_SKIP = new HashSet<>(
            Arrays.asList(MybatisAutoConfiguration.class.getCanonicalName(), DataSourceAutoConfiguration.class.getCanonicalName()));

    @Override
    public boolean[] match(String[] classNames, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[classNames.length];

        for(int i = 0; i< classNames.length; i++) {
            matches[i] = !SHOULD_SKIP.contains(classNames[i]);
        }
        return matches;
    }
}
