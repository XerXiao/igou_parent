package com.xer.igou.controller;

import com.xer.igou.domain.ProductType;
import com.xer.igou.service.IBrandService;
import com.xer.igou.domain.Brand;
import com.xer.igou.query.BrandQuery;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    public IBrandService brandService;

    /**
     * 保存和修改公用的
     *
     * @param brand 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Brand brand) {
        try {
            if (brand.getId() != null) {
                brandService.updateById(brand);
            } else {
                brandService.insert(brand);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            brandService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取数据
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Brand get(@PathVariable(value = "id", required = true) Long id) {
        return brandService.selectById(id);
    }


    /**
     * 查看所有的品牌信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Brand> list() {
        return brandService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Brand> json(@RequestBody BrandQuery query) {
        return brandService.selectPageList(query);
    }


    /**
     * 获取品牌信息表
     *
     * @return
     */
    @RequestMapping(value = "/treeData", method = RequestMethod.GET)
    public List<Brand> brands() {
        return brandService.getDataTree();
    }
}
