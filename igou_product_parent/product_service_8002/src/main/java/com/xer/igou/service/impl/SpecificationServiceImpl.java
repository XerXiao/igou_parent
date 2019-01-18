package com.xer.igou.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.Specification;
import com.xer.igou.domain.Specification;
import com.xer.igou.mapper.SpecificationMapper;
import com.xer.igou.mapper.SpecificationOptionMapper;
import com.xer.igou.query.SpecificationQuery;
import com.xer.igou.service.ISpecificationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-18
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {
    public static final String KEY_IN_REDIS = "Specification_in_redis";
    public static final String TOTAL_IN_REDIS = "Specification_total_in_redis";

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private SpecificationMapper specificationMapper;
    @Override
    public PageList<Specification> selectPageList(SpecificationQuery query) {
        List<Specification> rows = null;
        Page<Specification> page = null;
        Long total = 0L;

        //高级查询从数据库获取值
        if(!"".equals(query.getKeyword()) && query.getKeyword() != null) {
//            System.out.println("高级查询获取");
            page = new Page<>(0,query.getRows());
            rows = specificationMapper.getAllSpecifications(page, query);
            total = page.getTotal();
            return new PageList<>(total,rows);
        }
        //从缓存中获取
        String specification_in_redis = redisClient.get(KEY_IN_REDIS+query.getPage());
        if(StringUtils.isNotBlank(specification_in_redis)) {
            //如果有对应值
//            System.out.println("第"+query.getPage()+"页缓存获取");
            //转换为数组返回
            rows = JSONArray.parseArray(specification_in_redis, Specification.class);
            //拿到对应总条数
            total = Long.parseLong(redisClient.get(TOTAL_IN_REDIS));
        }else {
//            System.out.println("第"+query.getPage()+"页数据库获取");
            page = new Page<>(query.getPage(),query.getRows());
            //缓存中没有,到数据库中查询
            rows = specificationMapper.getAllSpecifications(page, query);
            total = page.getTotal();
            //同步更新缓存
            String redisStr = JSONArray.toJSONString(rows);
            redisClient.set(KEY_IN_REDIS+query.getPage(),redisStr);
            total = page.getTotal();
            redisClient.set(TOTAL_IN_REDIS,total.toString());
        }
        return new PageList<>(total,rows);
    }

    @Override
    public boolean insert(Specification entity) {
        try {
            super.insert(entity);
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Serializable id) {
        try {
            super.deleteById(id);
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        try {
            super.deleteBatchIds(idList);
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateById(Specification entity) {
        try {
            super.updateById(entity);
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
