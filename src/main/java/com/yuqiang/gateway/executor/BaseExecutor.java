package com.yuqiang.gateway.executor;

import com.alibaba.fastjson.JSON;
import com.yuqiang.gateway.datasource.Connection;
import com.yuqiang.gateway.executor.result.GatewayResult;
import com.yuqiang.gateway.mapping.HttpStatement;
import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.type.SimpleTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class BaseExecutor implements Executor {

    private Logger logger = LoggerFactory.getLogger(BaseExecutor.class);

    protected Configuration configuration;
    protected Connection connection;

    public BaseExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.connection = connection;
    }

    @Override
    public GatewayResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception {
        // 参数处理；后续的一些参数校验也可以在这里封装。
        String methodName = httpStatement.getMethodName();
        String parameterType = httpStatement.getParameterType();
        String[] parameterTypes = new String[]{parameterType};
        Object[] args = SimpleTypeRegistry.isSimpleType(parameterType) ? params.values().toArray() : new Object[]{params};
        logger.info("执行调用 method：{}#{}.{}({}) args：{}", httpStatement.getApplication(), httpStatement.getInterfaceName(), httpStatement.getMethodName(), JSON.toJSONString(parameterTypes), JSON.toJSONString(args));
        // 抽象方法
        try {
            Object data = doExec(methodName, parameterTypes, args);
            return GatewayResult.buildSuccess(data);
        } catch (Exception e) {
            return GatewayResult.buildError(e.getMessage());
        }
    }

    protected abstract Object doExec(String methodName, String[] parameterTypes, Object[] args);

}
