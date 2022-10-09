package com.open.es.service;

import cn.hutool.core.collection.ListUtil;
import com.open.es.repository.database.Goods;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:48
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsQueryDataServiceTest {

    @Resource
    EsQueryDataService esQueryDataService;

    /**
     * 单字段精确查询
     */
    @Test
    public void termQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {
            goodsList = esQueryDataService.termQuery("goods", "title", "华为", Goods.class);
        } catch (Exception e) {
            log.error("单字段精确查询失败，错误信息：");
        }
        System.out.println("单字段精确查询结果：" + goodsList);
    }

    /**
     * 单字段多内容精确查询
     */
    @Test
    public void termsQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {
            String[] args = {"华为", "OPPO", "TCL"};

            goodsList = esQueryDataService.termsQuery("goods", "title", args, Goods.class);

        } catch (Exception e) {
            log.error("单字段多内容精确查询失败，错误信息：");
        }
        System.out.println("单字段多内容精确查询结果：" + goodsList);
    }

    /**
     * 单字段匹配分页查询
     */
    @Test
    public void matchQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {
            List<String> orderList = ListUtil.toList("-price", "-saleNum");

            goodsList = esQueryDataService.matchAllQuery("goods", Goods.class, 0, 3, orderList, "title", "华为");

        } catch (Exception e) {
            log.error("匹配查询失败，错误信息：");
        }
        System.out.println("匹配查询结果：" + goodsList);
    }


    /**
     * 单字段多内容精确查询
     */
    @Test
    public void matchPhraseQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {

            goodsList = esQueryDataService.matchPhraseQuery("goods", Goods.class, "title", "华为");

        } catch (Exception e) {
            log.error("词语匹配查询失败，错误信息：");
        }

        System.out.println("词语匹配查询结果：" + goodsList);
    }

    /**
     * 内容在多字段中进行查询
     */
    @Test
    public void matchMultiQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {
            String[] fields = {"title", "categoryName"};

            goodsList = esQueryDataService.matchMultiQuery("goods", Goods.class, fields, "手机");

        } catch (Exception e) {
            log.error("内容在多字段中进行查询失败，错误信息：");
        }

        System.out.println("内容在多字段中进行查询结果：" + goodsList);
    }

    /**
     * 通配符查询
     * <p>
     * 查询所有以 “三” 结尾的商品信息
     */
    @Test
    public void wildcardQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {

            goodsList = esQueryDataService.wildcardQuery("goods", Goods.class, "title", "*三");

        } catch (Exception e) {
            log.error("通配符查询查询失败，错误信息：");
        }

        System.out.println("通配符查询结果：" + goodsList);
    }

    /**
     * 模糊查询
     * <p>
     * 模糊查询所有以 “三” 结尾的商品信息
     */
    @Test
    public void fuzzyQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {

            goodsList = esQueryDataService.fuzzyQuery("goods", Goods.class, "title", "三");

        } catch (Exception e) {
            log.error("模糊查询失败，错误信息：");
        }

        System.out.println("模糊查询结果：" + goodsList);
    }


    @Test
    public void boolQuery() {
        // 返回数据
        List<Goods> goodsList = null;
        try {

            goodsList = esQueryDataService.boolQuery("goods", Goods.class);

        } catch (Exception e) {
            log.error("布尔查询失败，错误信息：");
        }

        System.out.println("布尔查询结果：" + goodsList);
    }

    /**
     * Metric 指标聚合分析
     */
    @Test
    public void metricQuery() {
        esQueryDataService.metricQuery("goods");
    }


    /**
     * Bucket 分桶聚合分析
     */
    @Test
    public void bucketQuery() {
        esQueryDataService.bucketQuery("goods", "brandName", "brandNameName");
    }

    /**
     * 子聚合聚合查询
     */
    @Test
    public void subBucketQuery() {
        esQueryDataService.subBucketQuery("goods", "brandName", "brandNameName", "price", "avgPrice");
    }

    /**
     * 综合聚合查询
     */
    @Test
    public void subSubAgg() {
        esQueryDataService.subSubAgg("goods");
    }


}
