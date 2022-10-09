package com.open.es.service;

import com.open.es.HelloEsInductionApplication;
import com.open.es.repository.database.Goods;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:36
 * @Description 文档操作
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloEsInductionApplication.class)
public class DocumentServiceTest {

    @Resource
    DocumentService documentService;

    /**
     * 添加文档
     */
    @Test
    public void addDocument() {
        // 创建商品信息
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setTitle("Apple iPhone 13 Pro (A2639) 256GB 远峰蓝色 支持移动联通电信5G 双卡双待手机");
        goods.setPrice(new BigDecimal("8799.00"));
        goods.setStock(1000);
        goods.setSaleNum(599);
        goods.setCategoryName("手机");
        goods.setBrandName("Apple");
        goods.setStatus(0);
        goods.setCreateTime(new Date());

        // 返回状态
        RestStatus restStatus = null;
        try {
            restStatus = documentService.addDocument("goods", "_doc", goods);
        } catch (Exception e) {
            log.error("添加文档失败，错误信息：");
        }
        System.out.println("添加文档响应状态：" + restStatus);
    }

    @Test
    public void getDocument() {

        // 返回信息
        Goods goods = null;
        try {
            goods = documentService.getDocument("goods", "_doc", "1");
        } catch (Exception e) {
            log.error("查询文档失败，错误信息：");
        }
        System.out.println("查询的文档信息：" + goods);
    }

    @Test
    public void updateDocument() {
        // 创建商品信息
        Goods goods = new Goods();
        goods.setTitle("Apple iPhone 13 Pro Max (A2644) 256GB 远峰蓝色 支持移动联通电信5G 双卡双待手机");
        goods.setPrice(new BigDecimal("9999"));
        goods.setId(1L);

        // 返回状态
        RestStatus restStatus = null;
        try {
            restStatus = documentService.updateDocument("goods", "_doc", goods);
        } catch (Exception e) {
            log.error("更新文档失败，错误信息：");
        }
        System.out.println("更新文档响应状态：" + restStatus);
    }

    @Test
    public void deleteDocument() {
        // 返回状态
        RestStatus restStatus = null;
        try {
            restStatus = documentService.deleteDocument("goods", "_doc", "1");
        } catch (Exception e) {
            log.error("删除文档失败，错误信息：");
        }
        System.out.println("删除文档响应状态：" + restStatus);
    }

    /**
     * 批量导入测试数据
     */
    @Test
    public void importDocument() {
        // 返回状态
        RestStatus restStatus = null;
        try {
            restStatus = documentService.batchImportGoodsData();
        } catch (Exception e) {
            log.error("批量导入数据失败，错误信息：");
        }
        System.out.println("批量导入数据响应状态：" + restStatus);
    }

}