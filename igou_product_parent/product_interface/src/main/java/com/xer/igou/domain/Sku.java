package com.xer.igou.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * SKU
 * </p>
 *
 * @author xer
 * @since 2019-01-19
 */
@TableName("t_sku")
public class Sku extends Model<Sku> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Date createTime = new Date();
    private Date updateTime = new Date();
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * SKU编码
     */
    private String code;
    private String name;
    /**
     * 市场价
     */
    private Integer marketPrice;
    /**
     * 优惠价
     */
    private Integer price;
    /**
     * 成本价
     */
    private Integer costPrice;
    /**
     * 销量
     */
    private Integer saleCount;
    /**
     * 排序
     */
    private Integer sortIndex;
    /**
     * 可用库存
     */
    private Integer availableStock;
    /**
     * 锁定库存
     */
    private Integer frozenStock;
    /**
     * SKU属性摘要
     */
    private String skuVlaues;
    /**
     * 预览图
     */
    private String skuMainPic;


    private Boolean state;
    private String index;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getFrozenStock() {
        return frozenStock;
    }

    public void setFrozenStock(Integer frozenStock) {
        this.frozenStock = frozenStock;
    }

    public String getSkuVlaues() {
        return skuVlaues;
    }

    public void setSkuVlaues(String skuVlaues) {
        this.skuVlaues = skuVlaues;
    }

    public String getSkuMainPic() {
        return skuMainPic;
    }

    public void setSkuMainPic(String skuMainPic) {
        this.skuMainPic = skuMainPic;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", productId=" + productId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", marketPrice=" + marketPrice +
                ", price=" + price +
                ", costPrice=" + costPrice +
                ", saleCount=" + saleCount +
                ", sortIndex=" + sortIndex +
                ", availableStock=" + availableStock +
                ", frozenStock=" + frozenStock +
                ", skuVlaues='" + skuVlaues + '\'' +
                ", skuMainPic='" + skuMainPic + '\'' +
                ", state=" + state +
                ", index='" + index + '\'' +
                '}';
    }
}
