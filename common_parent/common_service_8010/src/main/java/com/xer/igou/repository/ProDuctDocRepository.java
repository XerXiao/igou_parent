package com.xer.igou.repository;

import com.xer.igou.client.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProDuctDocRepository extends ElasticsearchRepository<ProductDoc,Long>{
}
