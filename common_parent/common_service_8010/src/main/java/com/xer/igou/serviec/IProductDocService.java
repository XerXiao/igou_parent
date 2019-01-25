package com.xer.igou.serviec;

import com.xer.igou.index.ProductDoc;
import com.xer.igou.util.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IProductDocService {
    /**
     * 添加索引
     * @param productDoc
     */
    void add(ProductDoc productDoc);

    /**
     * 批量添加
     * @param productDocs
     */
    void addBatch(List<ProductDoc> productDocs);

    /**
     * 删除
     * @param id
     */
    void del(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void delBatch(List<ProductDoc> ids);

    /**
     * 获取查询数据
     * @return
     */
    PageList<Map<String,Object>> search(Map<String,Object> map);
}