package com.github.xuchen93.es.learn;

import cn.hutool.core.util.IdUtil;
import com.github.xuchen93.es.learn.bean.IdxDocument;
import com.github.xuchen93.es.learn.data.DataService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class DataTests {
    @Autowired
    DataService dataService;
    @Autowired
    RestHighLevelClient client;

    @SneakyThrows
    @Test
    void insert() {
        dataService.insert(new IdxDocument().randomData());
    }

    @SneakyThrows
    @Test
    void batchInsert() {
        List<IdxDocument> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new IdxDocument().randomData());
        }
        dataService.batchInsert(list);
    }
}
