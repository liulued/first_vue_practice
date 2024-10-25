package com.lixin.firstSpring.controller;

import com.lixin.firstSpring.common.Result;
import com.lixin.firstSpring.entity.Admin;
import com.lixin.firstSpring.entity.Parms;
import com.lixin.firstSpring.service.AdminService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
       List<Admin> list= adminService.findBySearch(parms);
        return Result.success(list);
    }
}
