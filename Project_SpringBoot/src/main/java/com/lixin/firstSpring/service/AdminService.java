package com.lixin.firstSpring.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    /*public List<Admin> findBySearch(Parms parms) {
        //开启分页
        PageHelper.startPage(parms.getPageNum(),parms.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        return adminDao.findBySearch(parms);
    }*/
    public PageInfo<Admin> findBySearch(Parms parms) {
        //开启分页
        PageHelper.startPage(parms.getPageNum(),parms.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Admin> list = adminDao.findBySearch(parms);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        //合理性判断：同一名字的人禁止注册
        //根据用户名去数据库查询即可
        Admin user=adminDao.findByName(admin.getAdminName());
        if(user!=null){
            //说明已经有重复的用户，提示
        }
        //初始化一个密码
        if(admin.getPassword()==null){
            admin.setPassword("123456");
        }
        adminDao.insertSelective(admin);  //用于前端和后端Admin字段对应一致;
        //adminDao.add(admin);

    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }

    public Admin login(Admin admin) {
        //1. 进行非空判断

        //2. 从数据库中查询对应的用户的账号密码
        return null;
    }
}
