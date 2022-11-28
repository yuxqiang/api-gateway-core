package com.yuqiang;

import com.yuqiang.gateway.mapping.HttpCommandType;
import com.yuqiang.gateway.mapping.HttpStatement;
import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.session.defaults.DefaultGatewaySessionFactory;
import com.yuqiang.gateway.socket.GatewaySocketServer;
import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GatewaySessionTest {

    private final Logger logger = LoggerFactory.getLogger(GatewaySessionTest.class);
//
//    /**
//     * 测试：http://localhost:7397/wg/activity/sayHi
//     */
//    @Test
//    public void test_gateway1() throws InterruptedException, ExecutionException {
//        // 1. 创建配置信息加载注册
//        Configuration configuration = new Configuration();
//        HttpStatement httpStatement = new HttpStatement(
//                "api-gateway-test",
//                "cn.bugstack.gateway.rpc.IActivityBooth",
//                "sayHi",
//                "/wg/activity/sayHi",
//                HttpCommandType.GET);
//        configuration.addMapper(httpStatement);
//
//        // 2. 基于配置构建会话工厂
//        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
//
//        // 3. 创建启动网关网络服务
//        GatewaySocketServer server = new GatewaySocketServer(gatewaySessionFactory);
//
//        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
//        Channel channel = future.get();
//
//        if (null == channel) throw new RuntimeException("netty server start error channel is null");
//
//        while (!channel.isActive()) {
//            logger.info("netty server gateway start Ing ...");
//            Thread.sleep(500);
//        }
//        logger.info("netty server gateway start Done! {}", channel.localAddress());
//
//        Thread.sleep(Long.MAX_VALUE);
//    }
//
//
//    @Test
//    public void test_gateway() throws InterruptedException, ExecutionException {
//        // 1. 创建配置信息加载注册
//        Configuration configuration = new Configuration();
//        HttpStatement httpStatement = new HttpStatement(
//                "api-gateway-test",
//                "cn.bugstack.gateway.rpc.IActivityBooth",
//                "sayHi",
//                "/wg/activity/sayHi",
//                HttpCommandType.GET);
//        configuration.addMapper(httpStatement);
//
//        // 2. 基于配置构建会话工厂
//        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
//
//        // 3. 创建启动网关网络服务
//        GatewaySocketServer server = new GatewaySocketServer(gatewaySessionFactory);
//
//        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
//        Channel channel = future.get();
//
//        if (null == channel) throw new RuntimeException("netty server start error channel is null");
//
//        while (!channel.isActive()) {
//            logger.info("netty server gateway start Ing ...");
//            Thread.sleep(500);
//        }
//        logger.info("netty server gateway start Done! {}", channel.localAddress());
//
//        Thread.sleep(Long.MAX_VALUE);
//    }


    /**
     * 测试：
     * http://localhost:7397/wg/activity/sayHi
     * 参数：
     * {
     * "str": "10001"
     * }
     * <p>
     * http://localhost:7397/wg/activity/index
     * 参数：
     * {
     * "name":"小傅哥",
     * "uid":"10001"
     * }
     */
    @Test
    public void test_gateway() throws InterruptedException, ExecutionException {
        // 1. 创建配置信息加载注册
        Configuration configuration = new Configuration();

        HttpStatement httpStatement01 = new HttpStatement(
                "api-gateway-test",
                "cn.bugstack.gateway.rpc.IActivityBooth",
                "sayHi",
                "java.lang.String",
                "/wg/activity/sayHi",
                HttpCommandType.GET);

        HttpStatement httpStatement02 = new HttpStatement(
                "api-gateway-test",
                "cn.bugstack.gateway.rpc.IActivityBooth",
                "insert",
                "cn.bugstack.gateway.rpc.dto.XReq",
                "/wg/activity/insert",
                HttpCommandType.POST);

        configuration.addMapper(httpStatement01);
        configuration.addMapper(httpStatement02);

        // 2. 基于配置构建会话工厂
        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);

        // 3. 创建启动网关网络服务
        GatewaySocketServer server = new GatewaySocketServer(gatewaySessionFactory);

        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();

        if (null == channel) throw new RuntimeException("netty server start error channel is null");

        while (!channel.isActive()) {
            logger.info("netty server gateway start Ing ...");
            Thread.sleep(500);
        }
        logger.info("netty server gateway start Done! {}", channel.localAddress());

        Thread.sleep(Long.MAX_VALUE);
    }
}
