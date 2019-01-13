package com.xer.igou.controller;

import com.xer.igou.domain.Employee;
import com.xer.igou.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee) {
        if("admin".equals(employee.getName()) && "123".equals(employee.getPassword())) {
            return AjaxResult.me().setResultObject(new AjaxResult());
        }
        return AjaxResult.me().setSuccess(false).setMessage("用户名或密码错误").setErrorcode(10001);
    }
}
