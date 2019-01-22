package com.xer.igou.serviec;

import com.xer.igou.client.ProductDoc;

import java.util.List;

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
     * 获取
     * @param id
     * @return
     */
    ProductDoc get(Long id);

    /**
     * 获取列表
     * @return
     */
    List<ProductDoc> getList();
}
