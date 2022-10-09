package com.open.es.service;

import java.util.Map;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:21
 * @Description
 */
public interface IndexService {

    boolean indexCreate() throws Exception;

    Map<String, Object> getMapping(String indexName) throws Exception;

    boolean indexDelete(String indexName) throws Exception;

    boolean indexExists(String indexName) throws Exception;

}
