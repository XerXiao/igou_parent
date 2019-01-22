package com.xer.igou.serviec.impl;

import com.xer.igou.client.ProductDoc;
import com.xer.igou.repository.ProDuctDocRepository;
import com.xer.igou.serviec.IProductDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ProductDoc get(Long id) {
        return null;
    }

    @Override
    public List<ProductDoc> getList() {
        return null;
    }
}
