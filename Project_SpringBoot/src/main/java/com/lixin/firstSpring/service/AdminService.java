package com.lixin.firstSpring.service;

import com.lixin.firstSpring.common.Result;
import com.lixin.firstSpring.dao.AdminDao;
import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.entity.Parms;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Admin> getAdmin(){
        //return userDao.getUser();
        //方法3：利用Mybatis的包
        return adminDao.selectAll();
    }

    public List<Admin> findBySearch(Parms parms) {
        return adminDao.findBySearch(parms);
    }
}
