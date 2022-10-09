package com.open.es.service;

import com.open.es.repository.database.Goods;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:30
 * @Description
 */
public interface DocumentService {

    RestStatus addDocument(String indexName, String type, Goods goods) throws IOException;

    Goods getDocument(String indexName, String type, String id) throws Exception;

    RestStatus updateDocument(String indexName, String type, Goods goods) throws IOException;

    RestStatus deleteDocument(String indexName, String type, String id) throws IOException;

    RestStatus batchImportGoodsData() throws IOException;
}
