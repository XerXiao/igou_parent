package com.xer.igou.client;

import com.xer.igou.index.ProductDoc;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductDocClientFallbackFactory implements FallbackFactory<ProductDocClient>{
    @Override
    public ProductDocClient create(Throwable throwable) {
        return new ProductDocClient() {
            @Override
            public AjaxResult add(ProductDoc prodcutDoc) {
                return null;
            }

            @Override
            public AjaxResult addBatch(List<ProductDoc> prodcutDocs) {
                return null;
            }

            @Override
            public AjaxResult del(Long id) {
                return null;
            }

            @Override
            public AjaxResult delBatch(List<ProductDoc> ids) {
                return null;
            }

            @Override
            public PageList<Map<String, Object>> search(Map<String, Object> map) {
                return null;
            }
        };
    }
}
