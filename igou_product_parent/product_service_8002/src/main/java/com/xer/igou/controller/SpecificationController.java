package com.xer.igou.controller;

import com.xer.igou.service.ISpecificationService;
import com.xer.igou.domain.Specification;
import com.xer.igou.query.SpecificationQuery;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    public ISpecificationService specificationService;

    /**
    * 保存和修改公用的
    * @param specification  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Specification specification){
        try {
            if(specification.getId()!=null){
                specificationService.updateById(specification);
            }else{
                specificationService.insert(specification);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            specificationService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Specification get(@PathVariable(value="id",required=true) Long id)
    {
        return specificationService.selectById(id);
    }


    /**
    * 查
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public PageList<Specification> list(@RequestBody SpecificationQuery query){

        return specificationService.selectPageList(query);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Specification> json(@RequestBody SpecificationQuery query)
    {
        return specificationService.selectPageList(query);
    }
}
