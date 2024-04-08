package com.syx.mail.search;


import com.alibaba.fastjson.JSON;
import com.syx.common.utils.PageUtils;
import com.syx.mail.search.config.MailElasticSearchConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailSearchApplicationTests {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void searchData() throws IOException {
        // 创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("bank");
        // 指定DSL，检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构造检索条件
        searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(20);
        searchSourceBuilder.aggregation(ageAgg);
        AvgAggregationBuilder balanceAgg = AggregationBuilders.avg("balanceAgg").field("balance");
        searchSourceBuilder.aggregation(balanceAgg);
        log.info(searchSourceBuilder.toString());

        searchRequest.source(searchSourceBuilder);



        SearchResponse search = client.search(searchRequest, MailElasticSearchConfig.COMMON_OPTIONS);
        log.info(search.toString());
        // 获取命中记录
        SearchHits hits = search.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            // 这里将数据变为string，可以构造对应的实体类通过json转换
            String sourceAsString = searchHit.getSourceAsString();

        }
        // 获取这次检索分析的信息
        Aggregations aggregations = search.getAggregations();
//        aggregations.forEach(aggregation -> {
//            log.info(aggregation.getName());
//        });
        Terms ageAgg1 = aggregations.get("ageAgg");
        ageAgg1.getBuckets().forEach(bucket -> {
            String keyAsString = bucket.getKeyAsString();
            log.info("年龄" + keyAsString);
        });

        Avg balanceAvg=  aggregations.get("balanceAgg");
        log.info("平均薪资"+balanceAvg.getValue());
    }


    @Test
    public void indexTest() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");
        User user = new User();
        user.setAge(19);
        user.setGender("F");
        user.setUserName("小红");
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);
        IndexResponse index = client.index(indexRequest, MailElasticSearchConfig.COMMON_OPTIONS);

        log.info(index.toString());
    }

    @Data
    class User {
        private String userName;
        private String gender;
        private Integer age;
    }

    @Test
    public void contextLoads() {
        log.info(client.toString());
    }

}
