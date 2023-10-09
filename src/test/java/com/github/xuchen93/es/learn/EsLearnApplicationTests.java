package com.github.xuchen93.es.learn;

import lombok.SneakyThrows;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EsLearnApplicationTests {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @SneakyThrows
    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient.indices().exists(new GetIndexRequest("idx_test"), RequestOptions.DEFAULT));
    }

}
