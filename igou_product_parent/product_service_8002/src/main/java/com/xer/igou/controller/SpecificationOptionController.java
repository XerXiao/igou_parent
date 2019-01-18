package com.xer.igou.controller;

import com.xer.igou.domain.Specification;
import com.xer.igou.service.ISpecificationOptionService;
import com.xer.igou.domain.SpecificationOption;
import com.xer.igou.query.SpecificationOptionQuery;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {
    @Autowired
    public ISpecificationOptionService specificationOptionService;

    /**
    * 保存和修改公用的
    * @param specificationOption  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody SpecificationOption specificationOption){
        try {
            if(specificationOption.getId()!=null){
                specificationOptionService.updateById(specificationOption);
            }else{
                specificationOptionService.insert(specificationOption);
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
            specificationOptionService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public SpecificationOption get(@PathVariable(value="id",required=true) Long id)
    {
        return specificationOptionService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<SpecificationOption> list(){

        return specificationOptionService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<SpecificationOption> json(@RequestBody SpecificationOptionQuery query)
    {
        Page<SpecificationOption> page = new Page<SpecificationOption>(query.getPage(),query.getRows());
        page = specificationOptionService.selectPage(page);
        return new PageList<SpecificationOption>(page.getTotal(),page.getRecords());
    }
}
