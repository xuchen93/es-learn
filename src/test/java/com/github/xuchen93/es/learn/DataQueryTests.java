package com.github.xuchen93.es.learn;

import cn.hutool.json.JSONUtil;
import com.github.xuchen93.es.learn.bean.IdxDocument;
import com.github.xuchen93.es.learn.data.DataService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class DataQueryTests {
    @Autowired
    RestHighLevelClient client;
    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @SneakyThrows
    @Test
    void insert() {
        GeoDistanceSortBuilder geoDistanceSortBuilder = SortBuilders
                .geoDistanceSort("geo_field",new GeoPoint(31.254187,120.737163))
                .unit(DistanceUnit.KILOMETERS)
                .order(SortOrder.ASC);
        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("text_field","江苏"));
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSort(geoDistanceSortBuilder)
                .build();
        SearchHits<IdxDocument> search = elasticsearchOperations.search(query, IdxDocument.class, IndexCoordinates.of("idx_document"));
        System.out.println(search.getTotalHits());
        search.getSearchHits().forEach(i->{
            System.out.println(i);
        });
    }
}
