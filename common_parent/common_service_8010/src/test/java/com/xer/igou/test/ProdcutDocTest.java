package com.xer.igou.test;


import com.xer.igou.CommonApplication_8010;
import com.xer.igou.client.ProductDoc;
import com.xer.igou.serviec.IProductDocService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApplication_8010.class)
public class ProdcutDocTest {

    @Autowired
    private ElasticsearchTemplate template;
    @Test
    public void testInit() {
        //创建索引
        template.createIndex(ProductDoc.class);
        template.putMapping(ProductDoc.class);
    }
}
