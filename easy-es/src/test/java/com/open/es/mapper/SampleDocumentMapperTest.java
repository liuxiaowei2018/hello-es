package com.open.es.mapper;

import com.open.es.HelloEasyEsApplication;
import com.open.es.pojo.SampleDocument;
import com.xpc.easyes.core.conditions.LambdaEsIndexWrapper;
import com.xpc.easyes.core.conditions.LambdaEsQueryWrapper;
import com.xpc.easyes.core.conditions.LambdaEsUpdateWrapper;
import com.xpc.easyes.core.enums.FieldType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 13:06
 * @Description
 */
@SpringBootTest(classes = HelloEasyEsApplication.class)
public class SampleDocumentMapperTest {

    @Resource
    SampleDocumentMapper sampleDocumentMapper;

    @Test
    public void testCreatIndex() {
        // 初始化-> 创建索引,相当于MySQL建表 | 此接口须首先调用,只调用一次即可
        LambdaEsIndexWrapper<SampleDocument> indexWrapper = new LambdaEsIndexWrapper<>();
        indexWrapper.indexName(SampleDocument.class.getSimpleName().toLowerCase());
        indexWrapper.mapping(SampleDocument::getTitle, FieldType.KEYWORD)
                .mapping(SampleDocument::getContent, FieldType.TEXT);
        Boolean isOk = sampleDocumentMapper.createIndex(indexWrapper);
        // return Boolean.TRUE;
        Assert.assertTrue(isOk);
        // 期望值: true 如果是true 则证明索引已成功创建
    }

    @Test
    public void testInsert() {
        // 测试插入数据
        SampleDocument sampleDocument = new SampleDocument();
        sampleDocument.setTitle("老汉");
        sampleDocument.setContent("推*技术过硬");
        Integer count = sampleDocumentMapper.insert(sampleDocument);
        System.out.println(count);
    }

    @Test
    public void testSelect() {
        // 测试查询
        String title = "老汉";
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(SampleDocument::getTitle, title);
        SampleDocument sampleDocument = sampleDocumentMapper.selectOne(wrapper);
        System.out.println(sampleDocument);
        Assert.assertEquals(title, sampleDocument.getTitle());
    }

    @Test
    public void testUpdate() {
        // 测试更新 更新有两种情况 分别演示如下:
        // case1: 已知id, 根据id更新 (为了演示方便,此id是从上一步查询中复制过来的,实际业务可以自行查询)
        String id = "DiqT4H8BMm6cqL6ZGEjQ";
        String title1 = "隔壁老王";
        SampleDocument sampleDocument1 = new SampleDocument();
        sampleDocument1.setId(id);
        sampleDocument1.setTitle(title1);
        sampleDocumentMapper.updateById(sampleDocument1);

        // case2: id未知, 根据条件更新
        LambdaEsUpdateWrapper<SampleDocument> wrapper = new LambdaEsUpdateWrapper<>();
        wrapper.eq(SampleDocument::getTitle, title1);
        SampleDocument sampleDocument2 = new SampleDocument();
        sampleDocument2.setTitle("隔壁老李");
        sampleDocument2.setContent("推*技术过软");
        sampleDocumentMapper.update(sampleDocument2, wrapper);

        // 关于case2 还有另一种省略实体的简单写法,这里不演示,后面章节有介绍,语法与MP一致

    }

    @Test
    public void testDelete() {
        // 测试删除数据 删除有两种情况:根据id删或根据条件删
        // 鉴于根据id删过于简单,这里仅演示根据条件删,以老李的名义删,让老李心理平衡些
        LambdaEsQueryWrapper<SampleDocument> wrapper = new LambdaEsQueryWrapper<>();
        String title = "隔壁老李";
        wrapper.eq(SampleDocument::getTitle, title);
        int successCount = sampleDocumentMapper.delete(wrapper);
        System.out.println(successCount);
    }
}