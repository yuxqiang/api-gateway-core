package com.yuqiang.gateway.datasource;

import com.yuqiang.gateway.session.Configuration;

/**
 * 数据源工厂
 */
public interface DataSourceFactory {
    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();
}
