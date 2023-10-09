package com.github.xuchen93.es.learn.idx;

import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.springframework.stereotype.Component;

/**
 * @author xuchen.wang
 * @date 2023/9/28
 */
@Slf4j
@Component
public class IdxService {

    final
    RestHighLevelClient client;

    public IdxService(RestHighLevelClient client) {
        this.client = client;
    }

    @SneakyThrows
    public GetIndexResponse getIdx(String... idx){
        GetIndexRequest request = new GetIndexRequest(idx);
        return client.indices().get(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    public CreateIndexResponse createIdx(String idx){
        CreateIndexRequest request = new CreateIndexRequest(idx);
        return createIdx(request);
    }

    @SneakyThrows
    public CreateIndexResponse createIdx(CreateIndexRequest request){
        log.info(JSONUtil.toJsonPrettyStr(request.mappings()));
        return client.indices().create(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    public AcknowledgedResponse modifyIdx(PutMappingRequest request){
        log.info(request.toString());
        return client.indices().putMapping(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    public AcknowledgedResponse delIdx(String idx){
        DeleteIndexRequest request = new DeleteIndexRequest(idx);
        return client.indices().delete(request,RequestOptions.DEFAULT);
    }

}
