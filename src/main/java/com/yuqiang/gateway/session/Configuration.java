package com.yuqiang.gateway.session;

import com.yuqiang.gateway.authorization.IAuth;
import com.yuqiang.gateway.authorization.auth.AuthService;
import com.yuqiang.gateway.bind.IGenericReference;
import com.yuqiang.gateway.bind.MapperRegistry;
import com.yuqiang.gateway.datasource.Connection;
import com.yuqiang.gateway.executor.Executor;
import com.yuqiang.gateway.executor.SimpleExecutor;
import com.yuqiang.gateway.mapping.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/***
 * 会话生命周期配置项
 */
public class Configuration {
    private final MapperRegistry mapperRegistry = new MapperRegistry(this);
    private final Map<String, HttpStatement> httpStatements = new HashMap<>();
    // RPC 应用服务配置项 api-gateway-test
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();
    // RPC 注册中心配置项 nacos://127.0.0.1:8848
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();
    // RPC 泛化服务配置项 cn.bugstack.gateway.rpc.IActivityBooth
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    private final IAuth auth = new AuthService();
    public Configuration() {
    }


    public synchronized void registryConfig(String applicationName, String address, String interfaceName, String version) {
        if (applicationConfigMap.get(applicationName) == null) {
            ApplicationConfig application = new ApplicationConfig();
            application.setName(applicationName);
            application.setQosEnable(false);
            applicationConfigMap.put(applicationName, application);
        }

        if (registryConfigMap.get(applicationName) == null) {
            RegistryConfig registry = new RegistryConfig();
            registry.setAddress(address);
            registry.setRegister(false);
            registryConfigMap.put(applicationName, registry);
        }

        if (referenceConfigMap.get(interfaceName) == null) {
            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            reference.setInterface(interfaceName);
            reference.setVersion(version);
            reference.setGeneric("true");
            referenceConfigMap.put(interfaceName, reference);
        }
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public void addHttpStatement(HttpStatement httpStatement) {
        httpStatements.put(httpStatement.getUri(), httpStatement);
    }

    public HttpStatement getHttpStatement(String uri) {
        return httpStatements.get(uri);
    }

    public void addMapper(HttpStatement httpStatement) {
        mapperRegistry.addMapper(httpStatement);
    }

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        return mapperRegistry.getMapper(uri, gatewaySession);
    }

    public Executor newExecutor(Connection connection) {
        return new SimpleExecutor(this, connection);
    }

    public boolean authValidate(String uId, String token) {
        return auth.validate(uId, token);
    }

}
