package com.mini.db.config;

import org.springframework.core.env.Environment;

import java.util.List;

/**
 * 自定义数据源接口 凡是需要手动指定数据源，都需实现configDataSource接口
 * @author songjiuhua
 * Created by 2020/5/8 17:52
 */
public interface CustomDataSourceConfig {

    /**
     * 从环境变量中获取需要的参数，返回结构DbConfig对象中，dbSourceName和scanBasePackage必填
     * 如果返回空数组，则自定义配置数据源不生效，默认方案生效
     * 如果List<DbConfig>返回多条记录，需保证每个记录的scanBasePackage不包含相同的Mapper接口
     * 否则会启动失败（因为spring容器中bean名称不能重复）
     * @param environment
     * @return
     */
    List<DbConfig> configDataSource(Environment environment);

}
