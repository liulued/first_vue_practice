package com.lixin.firstSpring.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtTokenUtils {
    private static AdminService staticAdminService;
    private static final Logger log= LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private AdminService adminService;

    @PostConstruct
    public void setAdminService(){
        staticAdminService=adminService;
    }

    //生成token
    public static String getToken(String userId,String password){
        return JWT.create().withAudience(userId)   //将 user 的 id 保存到token里面，作为载荷
        .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时后token过期
        .sign(Algorithm.HMAC256(password)); //以password作为token密钥
    }

    //获取当前登录用户的信息
    public static Admin getCurrentUser(){
        String token=null;
        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token=request.getHeader("token");
            if(StrUtil.isBlank(token)){
                token=request.getParameter("token");
            }
            if(StrUtil.isBlank(token)){
                log.error("获取当前登录的token失败，token：{}",token);
                return null;
            }
            //解析token，获取用户id
            String adminId= JWT.decode(token).getAudience().get(0);
            return staticAdminService.findById(Integer.valueOf(adminId));
        }catch (Exception e){
            log.error("获取当前登录的管理员信息失败，token={}",token,e);
            return null;
        }
    }
}
