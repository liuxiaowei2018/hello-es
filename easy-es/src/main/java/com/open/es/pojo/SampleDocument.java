package com.open.es.pojo;

import com.xpc.easyes.core.anno.HighLightMappingField;
import com.xpc.easyes.core.anno.TableId;
import com.xpc.easyes.core.anno.TableName;
import com.xpc.easyes.core.enums.IdType;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年03月31日 17:36
 * @Description es操作文档示例
 */
@Data
@TableName("sample_document")
public class SampleDocument {

    /**
     * es中的唯一id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 高亮返回值被映射的字段
     */
    @HighLightMappingField("content")
    private String highlightContent;

}
