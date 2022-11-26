//package com.yuqiang;
//
//public class Apitest03 {
//
//    public void test_GenericReference(){
//
//        Config
//
//        Configuration configuration = new Configuration();
//        configuration.addGenericReference("api-gateway-test", "cn.bugstack.gateway.rpc.IActivityBooth", "sayHi");
//
//        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
//        Future<Channel> future = builder.build(configuration);
//
//        logger.info("服务启动完成 {}", future.get().id());
//
//        Thread.sleep(Long.MAX_VALUE);
//    }
//}
