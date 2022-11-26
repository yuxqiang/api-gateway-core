package com.yuqiang.gateway.session;

import com.yuqiang.gateway.session.defaults.GenericReferenceSessionFactory;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 会话工厂建造类
 */
public class GenericReferenceSessionFactoryBuilder {
    public Future<Channel> build(Configuration configuration) {
        IGenericReferenceSessionFactory genericReferenceSessionFactory = new GenericReferenceSessionFactory(configuration);
        try {
            return genericReferenceSessionFactory.openSession();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
