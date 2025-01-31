package com.lixin.firstSpring.controller;

import com.github.pagehelper.PageInfo;
import com.lixin.firstSpring.common.Result;
import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.entity.Parms;
import com.lixin.firstSpring.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    private static final Logger log= LoggerFactory.getLogger(AdminController.class);


    @GetMapping()
    public Result getAdmin(){
        List<Admin> list= adminService.getAdmin();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Parms parms){
        log.info("拦截器已运行，正式调用接口内部，查询管理员信息");
       PageInfo<Admin> info= adminService.findBySearch(parms);
        return Result.success(info);
    }

    @PostMapping()
    public Result save(@RequestBody Admin admin){
        if(admin.getId()==null){
            adminService.add(admin);
        }else{
            adminService.update(admin);
        }

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        Admin loginUser=adminService.login(admin);
        Map<String,Object> map=new HashMap<>();
        map.put("name",admin.getAdminName());
        map.put("password", admin.getPassword());
        return Result.success(map);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }
}
