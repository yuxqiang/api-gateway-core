package com.yuqiang.gateway.session.defaults;

import com.yuqiang.gateway.bind.IGenericReference;
import com.yuqiang.gateway.datasource.Connection;
import com.yuqiang.gateway.datasource.DataSource;
import com.yuqiang.gateway.mapping.HttpStatement;
import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.session.GatewaySession;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

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
    public Object get(String methodName, Object parameter) {

//        /* 以下这部分内容，后续拆到执行器中处理 */
//        // 配置信息
 //       HttpStatement httpStatement = configuration.getHttpStatement(uri);
//        String application = httpStatement.getApplication();
//        String interfaceName = httpStatement.getInterfaceName();
//
//        // 获取基础服务（创建成本较高，内存存放获取）
//        ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
//        RegistryConfig registryConfig = configuration.getRegistryConfig(application);
//        ReferenceConfig<GenericService> reference = configuration.getReferenceConfig(interfaceName);
//        // 构建Dubbo服务
//        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
//        bootstrap.application(applicationConfig).registry(registryConfig).reference(reference).start();
//        // 获取泛化调用服务
//        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
//        GenericService genericService = cache.get(reference);
//
//        return genericService.$invoke(httpStatement.getMethodName(), new String[]{"java.lang.String"}, new Object[]{"小傅哥"});
//
        Connection connection = dataSource.getConnection();
        return connection.execute(methodName, new String[]{"java.lang.String"}, new String[]{"name"}, new Object[]{parameter});

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
