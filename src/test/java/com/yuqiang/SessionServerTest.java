package com.yuqiang;

import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.session.GenericReferenceSessionFactoryBuilder;
import com.yuqiang.gateway.session.SessionServer;
import com.yuqiang.gateway.bind.*;
import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SessionServerTest {
    private final Logger logger = LoggerFactory.getLogger(SessionServerTest.class);

    @Test
    public void test() throws ExecutionException, InterruptedException {
        SessionServer server = new SessionServer(null);

        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty server start error channel is null");

        while (!channel.isActive()) {
            logger.info("NettyServer启动服务 ...");
            Thread.sleep(500);
        }
        logger.info("NettyServer启动服务完成 {}", channel.localAddress());
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void test_GenericReference() throws InterruptedException, ExecutionException {

        Configuration configuration = new Configuration();
        configuration.addGenericReference("api-gateway-test", "cn.bugstack.gateway.rpc.IActivityBooth", "sayHi");

        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
        Future<Channel> future = builder.build(configuration);
//
        logger.info("服务启动完成 {}", future.get().id());
//
        Thread.sleep(Long.MAX_VALUE);
    }
}
