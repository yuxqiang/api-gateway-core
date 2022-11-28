package com.yuqiang.gateway.datasource.unpooled;

import com.yuqiang.gateway.datasource.DataSource;
import com.yuqiang.gateway.datasource.DataSourceFactory;
import com.yuqiang.gateway.datasource.DataSourceType;
import com.yuqiang.gateway.session.Configuration;

public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setDataSourceType(DataSourceType.Dubbo);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

}
