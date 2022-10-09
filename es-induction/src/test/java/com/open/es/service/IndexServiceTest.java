package com.open.es.service;

import cn.hutool.json.JSONUtil;
import com.open.es.HelloEsInductionApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:26
 * @Description 索引操作
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloEsInductionApplication.class)
public class IndexServiceTest {

    @Resource
    IndexService indexService;

    /**
     * 创建索引库和映射表结构
     * 注意：索引一般不会这么创建
     */
    @Test
    public void indexCreate() {
        boolean flag = false;
        try {
            flag = indexService.indexCreate();
        } catch (Exception e) {
            log.error("创建索引失败，错误信息：");
        }
        System.out.println("创建索引是否成功：" + flag);
    }

    /**
     * 获取索引表结构
     */
    @Test
    public void getMapping() {
        try {
            Map<String, Object> indexMap = indexService.getMapping("goods");
            String pretty1 = JSONUtil.toJsonStr(indexMap);
            log.info("索引信息：{}", pretty1);
        } catch (Exception e) {
            log.error("获取索引失败，错误信息：");
        }
    }

    /**
     * 删除索引库
     *
     */
    @Test
    public void deleteIndex() {
        boolean flag = false;
        try {
            flag = indexService.indexDelete("goods");
        } catch (Exception e) {
            log.error("删除索引库失败，错误信息：");
        }
        System.out.println("删除索引库是否成功：" + flag);
    }

    /**
     * 校验索引库是否存在
     *
     */
    @Test
    public void indexExists() {
        boolean flag = false;
        try {
            flag = indexService.indexExists("goods");
        } catch (Exception e) {
            log.error("校验索引库是否存在，错误信息：");
        }
        System.out.println("索引库是否存在：" + flag);
    }

}