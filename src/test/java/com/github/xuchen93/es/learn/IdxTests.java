package com.github.xuchen93.es.learn;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.json.JSONUtil;
import com.github.xuchen93.es.learn.idx.IdxService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest
class IdxTests {
    @Autowired
    IdxService idxService;
    @Autowired
    RestHighLevelClient client;

    @SneakyThrows
    @Test
    void getIdx() {
        GetIndexResponse response = idxService.getIdx("idx_document");
        Map<String, Settings> settingsMap = response.getSettings();
        System.out.println("=========settings=========");
        System.out.println(JSONUtil.toJsonPrettyStr(settingsMap));
        Map<String, String> dataStreams = response.getDataStreams();
        System.out.println("=========dataStreams=========");
        System.out.println(JSONUtil.toJsonPrettyStr(dataStreams));
        Map<String, Settings> defaultSettings = response.getDefaultSettings();
        System.out.println("=========defaultSettings=========");
        System.out.println(JSONUtil.toJsonPrettyStr(defaultSettings));
        System.out.println("=========mapping metadata=========");
        Map<String, MappingMetadata> mappingMetadataMap = response.getMappings();
        for (Map.Entry<String, MappingMetadata> entry : mappingMetadataMap.entrySet()) {
            System.out.println("current index = " + entry.getKey());
            System.out.println("current value = " + JSONUtil.toJsonPrettyStr(entry.getValue().sourceAsMap()));
        }
    }

    @SneakyThrows
    @Test
    void createIdx() {
//        System.out.println(JSONUtil.toJsonPrettyStr(idxService.createIdx("idx_test")));
        CreateIndexRequest request = new CreateIndexRequest("idx_document");
        Map<String, Object> map = MapBuilder.<String, Object>create()
                .put("properties", MapBuilder.<String, Object>create()
                        .put("id_field", MapBuilder.<String, Object>create().put("type", "long").build())
                        .put("age_field", MapBuilder.<String, Object>create().put("type", "integer").build())
                        .put("keyword_field", MapBuilder.<String, Object>create().put("type", "keyword").build())
                        .put("text_field", MapBuilder.<String, Object>create().put("type", "text").build())
                        .put("text2_field", MapBuilder.<String, Object>create().put("type", "text").build())
                        .put("geo_field", MapBuilder.<String, Object>create().put("type", "geo_point").build())
                        .build()
                )
                .build();
        request.mapping(JSONUtil.toJsonStr(map), XContentType.JSON);
        System.out.println(JSONUtil.toJsonPrettyStr(idxService.createIdx(request)));
    }

    @SneakyThrows
    @Test
    void deleteIdx() {
        System.out.println(JSONUtil.toJsonPrettyStr(idxService.delIdx("idx_document")));
    }

    @SneakyThrows
    @Test
    void modifyIdx() {
        PutMappingRequest request = new PutMappingRequest("idx_document");
        Map<String, Object> map = MapBuilder.<String, Object>create()
                .put("properties", MapBuilder.<String, Object>create()
                        .put("text_field", MapBuilder.<String, Object>create().put("type", "text").build())
                        .put("text3_field", MapBuilder.<String, Object>create().put("type", "text").build())
                        .build()
                )
                .build();
        request.source(map);
        System.out.println(JSONUtil.toJsonPrettyStr(idxService.modifyIdx(request)));
    }
}
