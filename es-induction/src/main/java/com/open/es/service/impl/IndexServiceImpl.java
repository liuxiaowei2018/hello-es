package com.open.es.service.impl;

import com.open.es.service.IndexService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:22
 * @Description
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean indexCreate() throws Exception {
        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest indexRequest = new CreateIndexRequest("goods");
        // 2、设置索引的settings
        // 3、设置索引的mappings
        String mapping = "{\n" +
                "\n" +
                "\t\t\"properties\": {\n" +
                "\t\t  \"brandName\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t  },\n" +
                "\t\t  \"categoryName\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t  },\n" +
                "\t\t  \"createTime\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
                "\t\t  },\n" +
                "\t\t  \"id\": {\n" +
                "\t\t\t\"type\": \"long\"\n" +
                "\t\t  },\n" +
                "\t\t  \"price\": {\n" +
                "\t\t\t\"type\": \"double\"\n" +
                "\t\t  },\n" +
                "\t\t  \"saleNum\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t  },\n" +
                "\t\t  \"status\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t  },\n" +
                "\t\t  \"stock\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t  },\n" +
                "\t\t\"spec\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t  },\n" +
                "\t\t  \"title\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "  }";
        // 4、 设置索引的别名
        // 5、 发送请求
        // 5.1 同步方式发送请求
        IndicesClient indicesClient = restHighLevelClient.indices();
        indexRequest.mapping(mapping, XContentType.JSON);

        // 请求服务器
        CreateIndexResponse response = indicesClient.create(indexRequest, RequestOptions.DEFAULT);

        return response.isAcknowledged();

    }

    @Override
    public Map<String, Object> getMapping(String indexName) throws Exception {
        IndicesClient indicesClient = restHighLevelClient.indices();

        // 创建get请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        // 发送get请求
        GetIndexResponse response = indicesClient.get(request, RequestOptions.DEFAULT);
        // 获取表结构
        Map<String, MappingMetaData> mappings = response.getMappings();
        Map<String, Object> sourceAsMap = mappings.get(indexName).getSourceAsMap();
        return sourceAsMap;

    }

    @Override
    public boolean indexDelete(String indexName) throws Exception {
        IndicesClient indicesClient = restHighLevelClient.indices();
        // 创建delete请求方式
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        // 发送delete请求
        AcknowledgedResponse response = indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);

        return response.isAcknowledged();
    }

    @Override
    public boolean indexExists(String indexName) throws Exception {
        IndicesClient indicesClient = restHighLevelClient.indices();
        // 创建get请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        // 判断索引库是否存在
        boolean result = indicesClient.exists(request, RequestOptions.DEFAULT);

        return result;

    }
}
