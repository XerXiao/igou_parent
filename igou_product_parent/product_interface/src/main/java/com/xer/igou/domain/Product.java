package com.xer.igou.domain;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import sun.plugin2.main.client.PrintBandDescriptor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
@TableName("t_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Date createTime = new Date();
    private Date updateTime = new Date();
    /**
     * 商品名称
     */
    private String name;
    /**
     * 副名称
     */
    private String subName;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 商品类型ID
     */
    @TableField("product_type_id")
    private Long productTypeId;

    /**
     * 商品类型关联对象，不需要映射到数据库
     */
    @TableField(exist = false)
    private ProductType productType;

    /**
     * 上架时间
     */
    private Date onSaleTime;
    /**
     * 下架时间
     */
    private Date offSaleTime;

    /**
     * 商品品牌
     */
    @TableField("brand_id")
    private Long brandId;

    /**
     * 商品品牌关联对象
     */
    @TableField(exist = false)
    private Brand brand;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 最高价
     */
    private Integer maxPrice;
    /**
     * 最低价
     */
    private Integer minPrice;

    /**
     * 销量
     */
    private Integer saleCount;
    /**
     * 浏览量
     */
    private Integer viewCount;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 评分
     */
    private Integer commentScore;
    /**
     * 显示属性摘要
     */
    private String viewProperties;
    private Integer goodCommentCount;
    private Integer commonCommentCount;
    private Integer badCommentCount;

    /**
     * 接收简介与详情信息，垂直分表
     */
    @TableField(exist = false)
    private ProductExt productExt = new ProductExt();

    /**
     * 逻辑删除字段
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted = 0;

    public ProductExt getProductExt() {
        return productExt;
    }

    public void setProductExt(ProductExt productExt) {
        this.productExt = productExt;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getOnSaleTime() {
        return onSaleTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setOnSaleTime(Date onSaleTime) {
        this.onSaleTime = onSaleTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getOffSaleTime() {
        return offSaleTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setOffSaleTime(Date offSaleTime) {
        this.offSaleTime = offSaleTime;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
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

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    public String getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(String viewProperties) {
        this.viewProperties = viewProperties;
    }

    public Integer getGoodCommentCount() {
        return goodCommentCount;
    }

    public void setGoodCommentCount(Integer goodCommentCount) {
        this.goodCommentCount = goodCommentCount;
    }

    public Integer getCommonCommentCount() {
        return commonCommentCount;
    }

    public void setCommonCommentCount(Integer commonCommentCount) {
        this.commonCommentCount = commonCommentCount;
    }

    public Integer getBadCommentCount() {
        return badCommentCount;
    }

    public void setBadCommentCount(Integer badCommentCount) {
        this.badCommentCount = badCommentCount;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
        ", id=" + id +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", name=" + name +
        ", subName=" + subName +
        ", code=" + code +
        ", productTypeId=" + productTypeId +
        ", onSaleTime=" + onSaleTime +
        ", offSaleTime=" + offSaleTime +
        ", brandId=" + brandId +
        ", state=" + state +
        ", maxPrice=" + maxPrice +
        ", minPrice=" + minPrice +
        ", saleCount=" + saleCount +
        ", viewCount=" + viewCount +
        ", commentCount=" + commentCount +
        ", commentScore=" + commentScore +
        ", viewProperties=" + viewProperties +
        ", goodCommentCount=" + goodCommentCount +
        ", commonCommentCount=" + commonCommentCount +
        ", badCommentCount=" + badCommentCount +
        "}";
    }
}
