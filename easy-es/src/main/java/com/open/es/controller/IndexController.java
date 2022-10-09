package com.open.es.controller;

import com.open.es.mapper.SampleDocumentMapper;
import com.open.es.pojo.SampleDocument;
import com.xpc.easyes.core.conditions.LambdaEsIndexWrapper;
import com.xpc.easyes.core.enums.Analyzer;
import com.xpc.easyes.core.enums.FieldType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author liuxiaowei
 * @date 2022年04月09日 19:09
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/es/admin")
public class IndexController {

    @Resource
    private SampleDocumentMapper sampleDocumentMapper;

    /**
     * 创建索引
     * @date 2022/10/9 11:19
     * @return R
     */
    @GetMapping("/index")
    public String createIndex() {
        // 初始化-> 创建索引,相当于MySQL建表 | 此接口须首先调用,只调用一次即可
        LambdaEsIndexWrapper<SampleDocument> indexWrapper = new LambdaEsIndexWrapper<>();
        indexWrapper.indexName("sample_document");
        //indexWrapper.indexName(SampleDocument.class.getSimpleName().toLowerCase());
        indexWrapper.mapping(SampleDocument::getTitle, FieldType.KEYWORD)
                .mapping(SampleDocument::getContent, FieldType.TEXT, Analyzer.IK_MAX_WORD);
        sampleDocumentMapper.createIndex(indexWrapper);
        return "ok";
    }

    /**
     * 向索引插入数据
     * @date 2022/10/9 11:19
     * @return R
     */
    @PostMapping("/insert")
    public String insert(@RequestBody SampleDocument sampleDocument) {
        // 初始化-> 新增数据
        if (sampleDocumentMapper.insert(sampleDocument) > 0) {
            return "ok";
        }
        return "error";
    }

}
