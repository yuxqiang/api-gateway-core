package com.yuqiang.gateway.socket.handlers;

import com.yuqiang.gateway.bind.IGenericReference;
import com.yuqiang.gateway.executor.result.SessionResult;
import com.yuqiang.gateway.session.BaseHandler;
import com.yuqiang.gateway.session.GatewaySession;
import com.yuqiang.gateway.session.defaults.DefaultGatewaySessionFactory;
import com.yuqiang.gateway.socket.aggreement.AgreementConstants;
import com.yuqiang.gateway.socket.aggreement.GatewayResultMessage;
import com.yuqiang.gateway.socket.aggreement.RequestParser;
import com.yuqiang.gateway.socket.aggreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ProtocolDataHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(ProtocolDataHandler.class);

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    public ProtocolDataHandler(DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求【消息】 uri：{} method：{}", request.uri(), request.method());
        try {
            // 1. 解析请求参数
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();
            if (null == uri) return;
            Map<String, Object> args = requestParser.parse();

            // 2. 调用会话服务
            GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
            IGenericReference reference = gatewaySession.getMapper();
            SessionResult result = reference.$invoke(args);

            // 3. 封装返回结果
            DefaultFullHttpResponse response = new ResponseParser().parse("0000".equals(result.getCode()) ? GatewayResultMessage.buildSuccess(result.getData()) : GatewayResultMessage.buildError(AgreementConstants.ResponseCode._404.getCode(), "网关协议调用失败！"));
            channel.writeAndFlush(response);
        } catch (Exception e) {
            // 4. 封装返回结果
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._502.getCode(), "网关协议调用失败！" + e.getMessage()));
            channel.writeAndFlush(response);
        }
    }

}
