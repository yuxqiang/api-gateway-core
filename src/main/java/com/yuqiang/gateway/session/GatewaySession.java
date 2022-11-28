package com.yuqiang.gateway.session;

import com.yuqiang.gateway.bind.IGenericReference;

/**
 * 用户处理网关 HTTP 请求
 */
public interface GatewaySession {
    Object get(String methodName, Object parameter);

    //IGenericReference getMapper(String uri);
    IGenericReference getMapper();

    Configuration getConfiguration();
}
