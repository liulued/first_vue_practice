package com.lixin.firstSpring.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.exception.CustomException;
import com.lixin.firstSpring.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log= LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        //1.从http请求中的header获取token
        String token =request.getHeader("token");
        if(StrUtil.isBlank(token)){
            //如果没拿到，就去参数当中拿     /api/admin?token=xxx & pageNum=xxx
            token=request.getParameter("token");
        }
        //2.开始执行认证
        if(StrUtil.isBlank(token)){
            throw new CustomException("无token，请重新登录");
        }
        //获取token中的adminId
        String userId;
        Admin admin;
        try{
            //对token进行解码，拿到Audience，然后得到Audience里面的userid
            userId= JWT.decode(token).getAudience().get(0);
            //根据token中的userid查询数据库
            admin=adminService.findById(Integer.parseInt(userId));
        }catch (Exception e){
            String errMsg="token验证失败，请重新登录";
            log.error(errMsg+",token="+token,e);
            throw new CustomException(errMsg);
        }
        if(admin==null){
            throw new CustomException("用户不存在，请重新登录");
        }
        try{
            //用户名密码加签验证token
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token);  //验证token
        }catch (JWTVerificationException e){
            throw new CustomException("token验证失败，请重新登录");
        }
        log.info("token验证成功，允许放行");
        return true;
    }
}
