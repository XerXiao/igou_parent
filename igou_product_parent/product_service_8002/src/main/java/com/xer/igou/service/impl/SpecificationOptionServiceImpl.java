package com.xer.igou.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.SpecificationOption;
import com.xer.igou.domain.SpecificationOption;
import com.xer.igou.mapper.SpecificationOptionMapper;
import com.xer.igou.query.SpecificationOptionQuery;
import com.xer.igou.service.ISpecificationOptionService;
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
 * 商品选项 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-18
 */
@Service
public class SpecificationOptionServiceImpl extends ServiceImpl<SpecificationOptionMapper, SpecificationOption> implements ISpecificationOptionService {



}
