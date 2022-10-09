package com.open.es.repository.database;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:19
 * @Description
 */
@Data
public class Goods {

    /**
     * 商品编号
     */
    private Long id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品销售数量
     */
    private Integer saleNum;

    /**
     * 商品分类
     */
    private String categoryName;

    /**
     * 商品品牌
     */
    private String brandName;

    /**
     * 上下架状态
     */
    private Integer status;

    /**
     * 说明书
     */
    private String spec;

    /**
     * 商品创建时间
     */
    private Date createTime;
}
