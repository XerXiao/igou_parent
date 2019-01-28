package com.xer.igou.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(indexName = "igou", type = "product")
public class ProductDoc {

    @Id
    private Long id;
    private Long productTypeId;
    private Long brandId;

    private String name;

    @Field(type = FieldType.Keyword)
    private String keyword;
    private Date onsaleTime;
    private Integer saleCount;
    private Integer viewCount;
    private Integer commentCount;
    private Integer minPrice;
    private Integer maxPrice;
    /**
     * 拼接了商品名称，商品类型名称，商品品牌名称，商品副名称
     * 使用空格分隔，会自动的对空格进行分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String all;

    @Field(type = FieldType.Keyword)
    private String viewProperties;
    @Field(type = FieldType.Keyword)
    private String skuProperties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(type = FieldType.Keyword)
    private List<String> medias = new ArrayList<>();


    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:dd",timezone = "GMT+8")
    public Date getOnsaleTime() {
        return onsaleTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:dd")
    public void setOnsaleTime(Date onsaleTime) {
        this.onsaleTime = onsaleTime;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(String viewProperties) {
        this.viewProperties = viewProperties;
    }

    public String getSkuProperties() {
        return skuProperties;
    }

    public void setSkuProperties(String skuProperties) {
        this.skuProperties = skuProperties;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }
}
