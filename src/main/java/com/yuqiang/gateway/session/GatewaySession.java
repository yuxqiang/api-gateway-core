package com.yuqiang.gateway.session;

import com.yuqiang.gateway.bind.IGenericReference;

import java.util.Map;

/**
 * 用户处理网关 HTTP 请求
 */
public interface GatewaySession {
    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    //Object get(String methodName, Object parameter);

    //IGenericReference getMapper(String uri);
    IGenericReference getMapper();

    Configuration getConfiguration();
}
