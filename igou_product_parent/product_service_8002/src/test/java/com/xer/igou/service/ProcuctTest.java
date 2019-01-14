package com.xer.igou.service;

import com.xer.igou.ProductServerApplication_8002;
import com.xer.igou.domain.Product;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServerApplication_8002.class)
public class ProcuctTest {

//    @Autowired
//    private IUserService userService;
//
//    /**
//     * 添加
//     */
//    @Test
//    public void testAdd() {
//        for (int i = 0; i<20;i++){
//            User user = new User();
//            user.setName("xer");
//            userService.insert(user);
//        }
//
//    }
//
//    /**
//     * 删除
//     */
//    @Test
//    public void testDel() {
//        userService.deleteById(2L);
//    }

    @Autowired
    private IProductService productService;
    @Test
    public void test() {
        PageList<Product> pageList = productService.selectPageList(new ProductQuery());
        System.out.println(pageList.getTotal());

        List<Product> rows = pageList.getRows();
        rows.forEach(row -> {
            System.out.println(row);
            System.out.println(row.getProductType());
        });
    }
}
