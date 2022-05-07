package com.hongzhe.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hongzhe.common.exception.CaptchaException;
import com.hongzhe.common.lang.Const;
import com.hongzhe.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptureFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();

        if ("/login".equals(url) && httpServletRequest.getMethod().equals("POST")) {

            try{
                // Verification code
                validate(httpServletRequest);
            } catch (CaptchaException exception) {

                // To the authentication failure handler
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // Verification code logic
    private void validate(HttpServletRequest httpServletRequest) {

        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("token");

        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("Verification code incorrect");
        }

        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KET, key))) {
            throw new CaptchaException("Verification code incorrect");
        }

        redisUtil.hdel(Const.CAPTCHA_KET, key);
    }
}