package com.open.es.pojo;

import com.xpc.easyes.core.anno.TableId;
import com.xpc.easyes.core.anno.TableName;
import com.xpc.easyes.core.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年04月20日 11:36
 * @Description
 */
@Data
@TableName("es_product")
public class EsProduct {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * keyword
     */
    private String productNo;

    private Long brandId;

    /**
     * keyword
     */
    private String brandName;

    private Long productCategoryId;

    /**
     * keyword
     */
    private String productCategoryName;

    private String pic;

    /**
     * text ik_max_word
     */
    private String name;

    /**
     * text ik_max_word
     */
    private String subTitle;

    /**
     * text ik_max_word
     */
    private String keywords;

    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;

    /**
     * Nested
     */
    private List<EsProductAttributeValue> attrValueList;

}
