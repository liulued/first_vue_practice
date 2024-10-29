package com.lixin.firstSpring.controller;

import com.github.pagehelper.PageInfo;
import com.lixin.firstSpring.common.Result;
import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.entity.Parms;
import com.lixin.firstSpring.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;


    @GetMapping()
    public Result getAdmin(){
        List<Admin> list= adminService.getAdmin();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Parms parms){
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
        return Result.success(loginUser);
    }
}
