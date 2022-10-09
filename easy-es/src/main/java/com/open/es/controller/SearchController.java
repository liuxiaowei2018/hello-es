package com.open.es.controller;

import com.open.es.mapper.SampleDocumentMapper;
import com.open.es.pojo.SampleDocument;
import com.xpc.easyes.core.common.PageInfo;
import com.xpc.easyes.core.conditions.LambdaEsQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年04月16日 11:22
 * @Description 通用搜索
 */
@Slf4j
@RestController
@RequestMapping("/es/search")
public class SearchController {

    @Resource
    private SampleDocumentMapper sampleDocumentMapper;

    /**
     * 基本等值查询
     * @date 2022/10/9 11:20
     * @param document
     * @return java.util.List<com.open.es.pojo.SampleDocument>
     */
    @GetMapping("/sample")
    public List<SampleDocument> search(@RequestBody SampleDocument document) {
        // 查询出所有标题为xx的文档列表
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(SampleDocument::getTitle, document.getTitle());
        return sampleDocumentMapper.selectList(wrapper);
    }

    /**
     * 模糊查询
     * @date 2022/10/9 11:20
     * @param document
     * @return java.util.List<com.open.es.pojo.SampleDocument>
     */
    @GetMapping("/sample/v1")
    public List<SampleDocument> searchV1(@RequestBody SampleDocument document) {
        // 查询出所有内容类似xxx的文档列表
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.like(SampleDocument::getContent, document.getContent());
        return sampleDocumentMapper.selectList(wrapper);
    }

    /**
     * 匹配查询
     * @date 2022/10/9 11:20
     * @param document
     * @return java.util.List<com.open.es.pojo.SampleDocument>
     */
    @GetMapping("/sample/v2")
    public List<SampleDocument> searchV2(@RequestBody SampleDocument document) {
        // 查询出所有内容类似xxx的文档列表
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.match(SampleDocument::getContent, document.getContent());
        return sampleDocumentMapper.selectList(wrapper);
    }

    /**
     * 高亮匹配查询
     * @date 2022/10/9 11:20
     * @param document
     * @return java.util.List<com.open.es.pojo.SampleDocument>
     */
    @GetMapping("/sample/v3")
    public List<SampleDocument> searchV3(@RequestBody SampleDocument document) {
        // 高亮查询出所有内容类似xxx的文档列表
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.match(SampleDocument::getContent, document.getContent());
        wrapper.highLight(SampleDocument::getContent);
        return sampleDocumentMapper.selectList(wrapper);
    }

    /**
     * 权重匹配查询
     * @date 2022/10/9 11:20
     * @return java.util.List<com.open.es.pojo.SampleDocument>
     */
    @GetMapping("/sample/v4")
    public List<SampleDocument> searchV4() throws IOException {
        // 高亮查询出所有内容类似xxx的文档列表
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        String keyword = "垃圾";
        float contentBoost = 5.0f;
        wrapper.match(SampleDocument::getContent, keyword, contentBoost);

        String title = "小垃圾";
        float titleBoost = 2.0f;
        wrapper.eq(SampleDocument::getTitle, title, titleBoost);

        SearchResponse response = sampleDocumentMapper.search(wrapper);
        System.out.println(response);

        // 需要得分,则通过SearchResponse返回
        // 如果不需要得分,只需要按照得分高的排名靠前返回,则直接用List接收即可.
        return sampleDocumentMapper.selectList(wrapper);
    }

    /**
     * 分页查询
     * @date 2022/10/9 13:02
     * @return com.xpc.easyes.core.common.PageInfo<com.open.es.pojo.SampleDocument>
     */
    @GetMapping("/sample/v4")
    public PageInfo<SampleDocument> searchV5() throws IOException {

        // 高亮查询出所有内容类似xxx的文档列表
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        String keyword = "垃圾";
        float contentBoost = 5.0f;
        wrapper.match(SampleDocument::getContent, keyword, contentBoost);
        String title = "小垃圾";
        float titleBoost = 2.0f;
        wrapper.eq(SampleDocument::getTitle, title, titleBoost);
        return sampleDocumentMapper.pageQuery(wrapper, 1, 10);
    }

}
