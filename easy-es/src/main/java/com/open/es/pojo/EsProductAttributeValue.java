package com.open.es.pojo;

/**
 * @author liuxiaowei
 * @date 2022年04月20日 11:44
 * @Description
 */
public class EsProductAttributeValue {

    private Long id;
    private Long productAttributeId;

    /**
     * 属性值 Keyword
     */
    private String value;

    /**
     * 属性参数：0->规格；1->参数
     */
    private Integer type;

    /**
     * 属性名称
     */
    private String name;

}
