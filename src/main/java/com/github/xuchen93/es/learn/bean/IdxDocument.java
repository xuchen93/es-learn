package com.github.xuchen93.es.learn.bean;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.xuchen93.es.learn.util.CommonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author xuchen.wang
 * @date 2023/10/8
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Document(indexName = "idx_document")
public class IdxDocument extends BaseEsBean{
    @Field("id_field")
    private Long idField;
    @Field("age_field")
    private Integer ageField;
    @Field("keyword_field")
    private String keywordField;
    @Field("text_field")
    private String textField;
    @Field("text2_field")
    private String text2Field;
    @Field("geo_field")
    private GeoPoint  geoField;


    public IdxDocument randomData(){
        idField = IdUtil.getSnowflake().nextId();
        ageField = RandomUtil.randomInt(80);
        keywordField = RandomUtil.randomString(10);
        CommonUtil.Area area = CommonUtil.randomArea();
        textField = area.getProvince() + area.randomCity();
        CommonUtil.Area area2 = CommonUtil.randomArea();
        text2Field = area2.getProvince() + area2.randomCity();
        geoField = new GeoPoint(RandomUtil.randomDouble(0,180),RandomUtil.randomDouble(0,180));
        return this;
    }
}
