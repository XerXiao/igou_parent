package com.xer.igou.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xer.igou.service.ISkuService;
import com.xer.igou.domain.Sku;
import com.xer.igou.query.SkuQuery;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    public ISkuService skuService;

    /**
    * 保存和修改公用的
    * @param sku  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Sku sku){
        try {
            if(sku.getId()!=null){
                skuService.updateById(sku);
            }else{
                skuService.insert(sku);
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
            skuService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Sku get(@PathVariable(value="id",required=true) Long id)
    {
        return skuService.selectById(id);
    }


    /**
    * 根据商品id获取对应的sku属性列表
    * @return
    */
    @RequestMapping(value = "/list/{id}",method = RequestMethod.GET)
    public List<Map<String,Object>> list(@PathVariable(value = "id",required = true) Long id){
        EntityWrapper<Sku> wrapper = new EntityWrapper<>();
        wrapper.eq("productId",id);
        //拆分
        List<Sku> skus = skuService.selectList(wrapper);
        List<Map<String,Object>> objects = new ArrayList<>();
        for (Sku sku : skus) {
            objects.add(getStringObjectMap(sku));
        }
        return objects;
    }

    private Map<String, Object> getStringObjectMap(Sku sku) {
        Map<String, Object> map = new LinkedHashMap<>();
        String skuVlaues = sku.getSkuVlaues();
        String[] values = skuVlaues.split("_");
        for (String value : values) {
            String[] v = value.split(":");
//            System.out.println(v[0]+":"+v[1]+":"+v[2]);
            if(v.length > 2) {
                map.put(v[0]+":"+v[1],v[2]);
            }
        }
        map.put("price",sku.getPrice());
        map.put("availableStock",sku.getAvailableStock());
        map.put("state",sku.getState());
        return map;
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Sku> json(@RequestBody SkuQuery query)
    {
        Page<Sku> page = new Page<Sku>(query.getPage(),query.getRows());
            page = skuService.selectPage(page);
            return new PageList<Sku>(page.getTotal(),page.getRecords());
    }
}
