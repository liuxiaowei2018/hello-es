package com.open.es.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.open.es.repository.database.Goods;
import com.open.es.repository.database.GoodsMapper;
import com.open.es.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:31
 * @Description
 */
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Resource
    GoodsMapper goodsMapper;

    /**
     * 增加文档信息
     */
    @Override
    public RestStatus addDocument(String indexName, String type, Goods goods) throws IOException {
        // 默认类型为_doc
        type = StrUtil.isBlank(type) ? "_doc" : type;
        // 将对象转为json
        String data = JSONUtil.toJsonStr(goods);
        // 创建索引请求对象
        IndexRequest indexRequest = new IndexRequest(indexName, type).id(goods.getId() + "").source(data, XContentType.JSON);
        // 执行增加文档
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        RestStatus status = response.status();

        log.info("创建状态：{}", status);

        return status;
    }

    /**
     * 获取文档信息
     */
    @Override
    public Goods getDocument(String indexName, String type, String id) throws Exception {
        // 默认类型为_doc
        type = StrUtil.isBlank(type) ? "_doc" : type;
        // 创建获取请求对象
        GetRequest getRequest = new GetRequest(indexName, type, id);
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> sourceAsMap = response.getSourceAsMap();
        Goods goods = JSONUtil.toBean(JSONUtil.toJsonStr(sourceAsMap), Goods.class);
        return goods;

    }

    /**
     * 更新文档信息
     */
    @Override
    public RestStatus updateDocument(String indexName, String type, Goods goods) throws IOException {
        // 默认类型为_doc
        type = StrUtil.isBlank(type) ? "_doc" : type;
        // 将对象转为json
        String data = JSONUtil.toJsonStr(goods);
        // 创建索引请求对象
        UpdateRequest updateRequest = new UpdateRequest(indexName, type, String.valueOf(goods.getId()));
        // 设置更新文档内容
        updateRequest.doc(data, XContentType.JSON);
        // 执行更新文档
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        log.info("创建状态：{}", response.status());
        RestStatus status = response.status();
        log.info("更新文档信息响应状态：{}", status);
        return status;
    }

    /**
     * 删除文档信息
     */
    @Override
    public RestStatus deleteDocument(String indexName, String type, String id) throws IOException {
        // 默认类型为_doc
        type = StrUtil.isBlank(type) ? "_doc" : type;
        // 创建删除请求对象
        DeleteRequest deleteRequest = new DeleteRequest(indexName, type, id);
        // 执行删除文档
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);

        RestStatus status = response.status();

        log.info("删除文档响应状态：{}", status);

        return status;
    }

    @Override
    public RestStatus batchImportGoodsData() throws IOException {
        //1.查询所有数据，mysql
        List<Goods> goodsList = goodsMapper.findAll();
        //2.bulk导入
        BulkRequest bulkRequest = new BulkRequest();
        //2.1 循环goodsList，创建IndexRequest添加数据
        for (Goods goods : goodsList) {
            //将goods对象转换为json字符串
            String data = JSONUtil.toJsonStr(goods);
            IndexRequest indexRequest = new IndexRequest("goods", "_doc");
            indexRequest.id(goods.getId() + "").source(data, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return response.status();
    }

}
