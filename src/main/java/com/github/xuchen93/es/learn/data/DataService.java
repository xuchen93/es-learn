package com.github.xuchen93.es.learn.data;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.xuchen93.es.learn.bean.BaseEsBean;
import com.sun.xml.internal.txw2.IllegalAnnotationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuchen.wang
 * @date 2023/10/8
 */
@Slf4j
@Component
public class DataService {

    private static final Map<Class<? extends BaseEsBean>,String> idxNameMap = new ConcurrentHashMap<>();

    final
    RestHighLevelClient client;

    public DataService(RestHighLevelClient client) {
        this.client = client;
    }

    @SneakyThrows
    public <T extends BaseEsBean> IndexResponse insert(T bean){
        return client.index(generateIdxRequest(bean), RequestOptions.DEFAULT);
    }

    @SneakyThrows
    public <T extends BaseEsBean> BulkResponse batchInsert(List<T> list){
        BulkRequest request = new BulkRequest();
        log.info("batchInsert-start");
        for (T t : list) {
            request.add(generateIdxRequest(t));
        }
        log.info("batchInsert-end");
        return client.bulk(request, RequestOptions.DEFAULT);
    }

    private <T extends BaseEsBean> IndexRequest generateIdxRequest(T bean){
        IndexRequest request = new IndexRequest();
        request.index(getIdxByAnalysisBean(bean));
        String json = JSONUtil.toJsonStr(BeanUtil.beanToMap(bean, true, true));
        log.info("[insert]:{}-{}",request.index(),json);
        request.source(json, XContentType.JSON);
        return request;
    }

    private <T extends BaseEsBean> String getIdxByAnalysisBean(T bean){
        return idxNameMap.computeIfAbsent(bean.getClass(),k->{
            if (k.getName().endsWith("$1")){
                k = (Class<? extends BaseEsBean>) k.getSuperclass();
            }
            log.info("解析{}的idxName",k.getName());
            Document document = k.getAnnotation(Document.class);
            if (document == null || StrUtil.isBlank(document.indexName())){
                throw new IllegalAnnotationException("bean缺少Document注解配置");
            }
            return document.indexName();
        });
    }
}
