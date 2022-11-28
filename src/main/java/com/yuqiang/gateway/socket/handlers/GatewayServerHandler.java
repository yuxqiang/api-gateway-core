package com.yuqiang.gateway.socket.handlers;

import com.yuqiang.gateway.mapping.HttpStatement;
import com.yuqiang.gateway.session.BaseHandler;
import com.yuqiang.gateway.session.Configuration;
import com.yuqiang.gateway.socket.aggreement.AgreementConstants;
import com.yuqiang.gateway.socket.aggreement.GatewayResultMessage;
import com.yuqiang.gateway.socket.aggreement.RequestParser;
import com.yuqiang.gateway.socket.aggreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);

    private final Configuration configuration;

    public GatewayServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
//        logger.info("网关接收请求 uri：{} method：{}", request.uri(), request.method());
//
//        // 解析请求参数
//        Map<String, Object> requestObj = new RequestParser(request).parse();
//        String uri = request.uri();
//        int idx = uri.indexOf("?");
//        uri = idx > 0 ? uri.substring(0, idx) : uri;
//        if (uri.equals("/favicon.ico")) {
//            return;
//        }
//        // 2. 调用会话服务
//        GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
//        IGenericReference reference = gatewaySession.getMapper();
//        Object result = reference.$invoke(requestObj);
//        // 3. 封装返回结果
//        DefaultFullHttpResponse response = new ResponseParser().parse(result);
//        channel.writeAndFlush(response);






        logger.info("网关接收请求【全局】 uri：{} method：{}", request.uri(), request.method());
        try {
            // 1. 解析参数
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();

            // 2. 保存信息；HttpStatement、Header=token
            HttpStatement httpStatement = configuration.getHttpStatement(uri);
            channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);
            // 3. 放行服务
            request.retain();
            ctx.fireChannelRead(request);
        } catch (Exception e) {
            // 4. 封装返回结果
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), "网关协议调用失败！" + e.getMessage()));
            channel.writeAndFlush(response);
        }
    }
}
