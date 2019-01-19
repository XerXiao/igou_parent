package com.xer.igou.domain;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author xer
 * @since 2019-01-19
 */
@TableName("t_specification")
public class Specification extends Model<Specification> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 规格名称
     */
    private String name;
    private Integer isSku;
    @TableField("product_type_id")
    private Long productTypeId;
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted = 0;

    /**
     * 编辑属性时设置的值
     */
    @TableField(exist = false)
    private String selectValue;

    /**
     * 编辑sku属性时设置的值
     */
    @TableField(exist = false)
    private List<String> skuValues = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsSku() {
        return isSku;
    }

    public void setIsSku(Integer isSku) {
        this.isSku = isSku;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public List<String> getSkuValues() {
        return skuValues;
    }

    public void setSkuValues(List<String> skuValues) {
        this.skuValues = skuValues;
    }

    @Override
    public String toString() {
        return "Specification{" +
        ", id=" + id +
        ", name=" + name +
        ", isSku=" + isSku +
        ", productTypeId=" + productTypeId +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
