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
public class DataQueryService {

    final
    RestHighLevelClient client;

    public DataQueryService(RestHighLevelClient client) {
        this.client = client;
    }


}
