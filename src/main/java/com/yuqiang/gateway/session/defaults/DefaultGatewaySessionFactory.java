package com.yuqiang.gateway.session.defaults;

import com.yuqiang.gateway.datasource.DataSource;
import com.yuqiang.gateway.datasource.DataSourceFactory;
import com.yuqiang.gateway.datasource.unpooled.UnpooledDataSourceFactory;
import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.session.GatewaySession;
import com.yuqiang.gateway.session.GatewaySessionFactory;

public class DefaultGatewaySessionFactory implements GatewaySessionFactory {
    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GatewaySession openSession(String uri) {
        // 获取数据源连接信息：这里把 Dubbo、HTTP 抽象为一种连接资源
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        return new DefaultGatewaySession(configuration,uri,dataSource);
    }

}
