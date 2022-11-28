package com.yuqiang.gateway.executor;

import com.yuqiang.gateway.executor.result.GatewayResult;
import com.yuqiang.gateway.mapping.HttpStatement;

import java.util.Map;

public interface Executor {
    GatewayResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
