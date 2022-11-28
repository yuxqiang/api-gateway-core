package com.yuqiang.gateway.executor;

import com.yuqiang.gateway.executor.result.SessionResult;
import com.yuqiang.gateway.mapping.HttpStatement;

import java.util.Map;

public interface Executor {
    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
