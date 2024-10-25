package com.lixin.firstSpring.dao;

import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.entity.Parms;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {
    //1. 基于注解的方法
    //@Select("select * from user")
    List<Admin> getAdmin();

    List<Admin> findBySearch(@Param("parms") Parms parms);
}
