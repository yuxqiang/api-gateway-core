package com.yuqiang.gateway.bind;

import com.yuqiang.gateway.executor.result.SessionResult;

import java.util.Map;

public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);
}
