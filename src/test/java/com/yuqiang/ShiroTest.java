package com.yuqiang;

import com.yuqiang.gateway.authorization.IAuth;
import com.yuqiang.gateway.authorization.JwtUtil;
import com.yuqiang.gateway.authorization.auth.AuthService;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小傅哥，微信：fustack
 * @description shiro + jwt
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * https://jwt.io/
 * https://shiro.apache.org/download.html#191Source
 * https://github.com/apache/shiro/blob/main/samples/quickstart/src/main/java/Quickstart.java
 */
public class ShiroTest {

    private final static Logger logger = LoggerFactory.getLogger(ShiroTest.class);

    @Test
    public void test_auth_service() {
        IAuth auth = new AuthService();
        boolean validate = auth.validate("xiaofuge", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4aWFvZnVnZSIsImV4cCI6MTY3MDIzMzQ2MSwiaWF0IjoxNjY5NjI4NjYxLCJrZXkiOiJ4aWFvZnVnZSJ9.boKn9rAx5CffU6kua09zor7H7sq0C_ZBbBMrRDGV4Fc");
        System.out.println(validate ? "验证成功" : "验证失败");
    }

    @Test
    public void test_awt() {
        String issuer = "xiaofuge";
        long ttlMillis = 7 * 24 * 60 * 60 * 1000L;
        Map<String, Object> claims = new HashMap<>();
        claims.put("key", "xiaofuge");

        // 编码
        String token = JwtUtil.encode(issuer, ttlMillis, claims);
        System.out.println(token);

        // 解码
        Claims parser = JwtUtil.decode(token);
        System.out.println(parser.getSubject());
    }

}
