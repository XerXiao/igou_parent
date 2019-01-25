package com.xer.igou.serviec.impl;

import java.lang.reflect.Field;
import java.util.*;

import com.xer.igou.index.ProductDoc;
import com.xer.igou.repository.ProDuctDocRepository;
import com.xer.igou.serviec.IProductDocService;
import com.xer.igou.util.PageList;
import com.xer.igou.util.XObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProductDocServiceImpl implements IProductDocService {

    @Autowired
    private ProDuctDocRepository repository;

    @Override
    public void add(ProductDoc productDoc) {
        repository.save(productDoc);
    }

    @Override
    public void addBatch(List<ProductDoc> productDocs) {
        repository.saveAll(productDocs);
    }


    @Override
    public void del(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delBatch(List<ProductDoc> ids) {
        repository.deleteAll(ids);
    }


    @Override
    public PageList<Map<String, Object>> search(Map<String, Object> map) {
        //keyword productTypeId brandId price sortField sortType minPrice maxPrice page rows
        //拿到前台传递参数
        String keyword = (String) map.get("keyword");
        String sortField = (String) map.get("sortField");
        String sortType = (String) map.get("sortType");

        Long productTypeId = map.get("productTypeId") != null ? Long.valueOf(map.get("productTypeId").toString()) : null;
        Long brandId = map.get("brandId") != null ?  Long.valueOf(map.get("brandId").toString()) : null;
        Long minPrice = map.get("minPrice") != null ?  Long.valueOf(map.get("minPrice").toString())*100 : null;
        Long maxPrice = map.get("maxPrice") != null ?  Long.valueOf(map.get("maxPrice").toString())*100 : null;
        Long page = Long.valueOf(map.get("page").toString());
        Long rows = Long.valueOf(map.get("rows").toString());

        //构建查询过滤架构
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        //设置查询条件   查询+过滤
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //查询
        if(StringUtils.isNotBlank(keyword)) {
            boolQuery.must(QueryBuilders.matchQuery("all",keyword));
        }
        //g过滤
        List<QueryBuilder> filter = boolQuery.filter();
        if (productTypeId != null) {
            filter.add(QueryBuilders.termQuery("productTypeId",productTypeId));
        }
        if(brandId != null) {
            filter.add(QueryBuilders.termQuery("brandId",brandId));
        }
        if(minPrice != null) {
            filter.add(QueryBuilders.rangeQuery("minPrice").gte(minPrice));

        }
        if(maxPrice != null) {
            filter.add(QueryBuilders.rangeQuery("maxPrice").lte(maxPrice));
        }

        builder.withQuery(boolQuery);
        //分页
        //由于当前页是从0开始，前台传递值需要-1
        if (StringUtils.isNotBlank(page.toString())) {
            page -= 1;
        }
        builder.withPageable(PageRequest.of(page.intValue(), rows.intValue()));
        //排序
        //指定默认排序
        SortOrder defaultValue = SortOrder.DESC;
        if (StringUtils.isNotBlank(sortField)) {
            if (StringUtils.isNotBlank(sortType) && !defaultValue.equals(sortType)) {
                defaultValue = SortOrder.ASC;
            }
            switch (sortField) {
                case "xl":
                    builder.withSort(SortBuilders.fieldSort("saleCount").order(defaultValue));
                    break;
                case "rq":
                    builder.withSort(SortBuilders.fieldSort("viewCount").order(defaultValue));
                    break;
                case "pl":
                    builder.withSort(SortBuilders.fieldSort("commentCount").order(defaultValue));
                    break;
                case "jg":
                    //根据排序规则来确定使用最低价格进行排序还是使用最高价格进行排序
                    if (SortOrder.DESC.equals(defaultValue)) {
                        //最高价
                        builder.withSort(SortBuilders.fieldSort("maxPrice").order(defaultValue));
                    } else {
                        //最低价
                        builder.withSort(SortBuilders.fieldSort("minPrice").order(defaultValue));
                    }
                    break;
                default:
                    break;
            }
//            builder.withSort(SortBuilders.fieldSort(sortField).order(defaultValue));
        }
        //封装数据
        Page<ProductDoc> result = repository.search(builder.build());
        List<Map<String,Object>> datas = XObjectUtils.object2MapList(result.getContent(),ProductDoc.class);

        return new PageList<Map<String,Object>>(result.getTotalElements(),datas);
    }
}
