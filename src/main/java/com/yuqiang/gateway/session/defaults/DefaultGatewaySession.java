package com.yuqiang.gateway.session.defaults;

import com.yuqiang.gateway.bind.IGenericReference;
import com.yuqiang.gateway.datasource.Connection;
import com.yuqiang.gateway.datasource.DataSource;
import com.yuqiang.gateway.mapping.HttpStatement;
import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.session.GatewaySession;
import com.yuqiang.gateway.type.SimpleTypeRegistry;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;

/***
 *  默认网关会话实现类
 */
public class DefaultGatewaySession implements GatewaySession {

    private Configuration configuration;

    private DataSource dataSource;
    private String uri;
    public DefaultGatewaySession(Configuration configuration, String uri, DataSource dataSource) {
        this.configuration = configuration;
        this.uri = uri;
        this.dataSource = dataSource;
    }

    @Override
    public Object get(String methodName, Map<String, Object> params) {
        Connection connection = dataSource.getConnection();
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        String parameterType = httpStatement.getParameterType();

        /*
         * 调用服务
         * 封装参数 PS：为什么这样构建参数，可以参考测试案例；cn.bugstack.gateway.test.RPCTest
         * 01(允许)：java.lang.String
         * 02(允许)：cn.bugstack.gateway.rpc.dto.XReq
         * 03(拒绝)：java.lang.String, cn.bugstack.gateway.rpc.dto.XReq —— 不提供多参数方法的处理
         * */
        return connection.execute(methodName,
                new String[]{parameterType},
                new String[]{"ignore"},
                SimpleTypeRegistry.isSimpleType(parameterType) ? params.values().toArray() : new Object[]{params});
    }

    @Override
    public Object post(String methodName, Map<String, Object> params) {
        return get(methodName, params);
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
