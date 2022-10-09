package com.open.es.service;

import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月09日 14:38
 * @Description
 */
public interface EsQueryDataService {

    <T> List<T> termQuery(String indexName, String columnName, Object value, Class<T> clazz);

    <T> List<T> termsQuery(String indexName, String columnName, Object[] dataArgs, Class<T> clazz);

    <T> List<T> matchAllQuery(String indexName, Class<T> clazz, int startIndex, int pageSize, List<String> orderList, String columnName, Object value);

    <T> List<T> matchPhraseQuery(String indexName, Class<T> clazz, String columnName, Object value);

    <T> List<T> matchMultiQuery(String indexName, Class<T> clazz, String[] fields, Object text);

    <T> List<T> wildcardQuery(String indexName, Class<T> clazz, String field, String text);

    <T> List<T> fuzzyQuery(String indexName, Class<T> clazz, String field, String text);

    <T> List<T> boolQuery(String indexName, Class<T> beanClass);

    void metricQuery(String indexName);

    void bucketQuery(String indexName, String bucketField, String bucketFieldAlias);

    void subBucketQuery(String indexName, String bucketField, String bucketFieldAlias, String avgFiled, String avgFiledAlias);

    void subSubAgg(String indexName);
}
