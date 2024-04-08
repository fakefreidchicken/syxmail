package com.syx.mail.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.syx.common.to.es.SkuEsModel;
import com.syx.mail.search.config.MailElasticSearchConfig;
import com.syx.mail.search.constant.EsConstant;
import com.syx.mail.search.service.MailSearchService;
import com.syx.mail.search.vo.SearchParam;
import com.syx.mail.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MailSearchServiceImpl <br/>
 * Description: <br/>
 * date: 2024/4/7 20:48 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Service
public class MailSearchServiceImpl implements MailSearchService {

    @Autowired
    RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam param) {

        SearchResult result = null;

        SearchRequest searchRequest = buildSearchRequest(param);
        try {
            SearchResponse response = client.search(searchRequest, MailElasticSearchConfig.COMMON_OPTIONS);


            result = buildSearchResult(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SearchRequest buildSearchRequest(SearchParam param) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        if (param.getCatalog3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        if (param.getBrandId() != null && param.getBrandId().size() > 0) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }

        if (param.getAttrs() != null && param.getAttrs().size() > 0) {


            param.getAttrs().forEach(attrStr -> {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                String[] s = attrStr.split("_");
                String attrId = s[0];
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                if (s.length > 1) {
                    String[] attrValues = s[1].split(":");
                    if (attrValues != null && attrValues.length > 0) {

                        nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));

                    }
                }
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);


            });

        }

        if (param.getHasStock() != null) {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));
        }
        if (!StringUtils.isEmpty(param.getSkuPrice())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");

            String[] s = param.getSkuPrice().split("_");
            if (s.length == 2) {
                rangeQuery.gte(s[0]).lte(s[1]);
            } else if (s.length == 1) {
                if (param.getSkuPrice().startsWith("_")) {
                    rangeQuery.lte(s[0]);
                }
                if (param.getSkuPrice().endsWith("_")) {
                    rangeQuery.gte(s[0]);
                }
            }
            boolQuery.filter(rangeQuery);
        }

        sourceBuilder.query(boolQuery);

        if(!StringUtils.isEmpty(param.getSort())) {
            String sort = param.getSort();
            String[] s = sort.split("_");
            SortOrder order= s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0],order);
        }

        if(param.getPageNum()!=null) {
            sourceBuilder.from((param.getPageNum() - 1)*EsConstant.PRODUCT_PAGESIZE);
            sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
        }

        if (!StringUtils.isEmpty(param.getKeyword())) {
            HighlightBuilder builder = new HighlightBuilder();
            builder.field("skuTitle");
            builder.preTags("<b style='color:red'>");
            builder.postTags("</b>");
            sourceBuilder.highlighter(builder);
        }

        // 聚合分析
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);

        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brand_agg);

        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(catalog_agg);

        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        attr_agg.subAggregation(attr_id_agg);
        sourceBuilder.aggregation(attr_agg);


        System.out.println(sourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
        return searchRequest;
    }

    private SearchResult buildSearchResult(SearchResponse response,SearchParam param) {
        SearchResult result = new SearchResult();
        SearchHits hits = response.getHits();
        List<SkuEsModel> esModels = new ArrayList<>();
        if(hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel esModel = JSON.parseObject(sourceAsString,SkuEsModel.class);
                esModels.add(esModel);
            }
        }
        result.setProducts(esModels);
        // 页数（total+pagesize-1）/pagesize
        return null;
    }
}
