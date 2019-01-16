package com.xer.igou.controller;

import com.xer.igou.util.AjaxResult;
import com.xer.igou.utils.FastDfsApiOpr;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FastDfsController {

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public AjaxResult upload(@RequestParam(value = "file",required = true) MultipartFile file) {
        try {
//            System.out.println("请求进入");
            //拿到后缀名
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            //拿到上传文件调用工具上传到服务器上
//            System.out.println("后缀名为："+suffix);
            String feedStr = FastDfsApiOpr.upload(file.getBytes(), suffix);
//            System.out.println("上传成功"+feedStr);
            //上传成功之后返回客户端文件的路径
            return AjaxResult.me().setResultObject(feedStr);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败"+e.getMessage());
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public AjaxResult upload(@RequestParam(value = "filePath",required = true) String filePath) {

        try {
            //获取要获取文件路径
            //把第一个/去掉
            String templateFilePath = filePath.substring(1);
            //拆分获取组名
            String groupName = templateFilePath.substring(0, templateFilePath.indexOf("/"));
            //拆分获取文件路径名.
            String filePathStr = templateFilePath.substring(templateFilePath.indexOf("/") + 1);
            FastDfsApiOpr.delete(groupName,filePathStr);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败"+e.getMessage());
        }
    }
}
