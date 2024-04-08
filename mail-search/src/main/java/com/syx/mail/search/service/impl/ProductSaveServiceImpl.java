package com.syx.mail.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.syx.common.to.es.SkuEsModel;
import com.syx.mail.search.config.MailElasticSearchConfig;
import com.syx.mail.search.constant.EsConstant;
import com.syx.mail.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ProductSaveServiceImpl <br/>
 * Description: <br/>
 * date: 2024/4/2 4:16 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        // 保存到es
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            // 构造保存请求
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String jsonString = JSON.toJSONString(skuEsModel);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bucket = restHighLevelClient.bulk(bulkRequest, MailElasticSearchConfig.COMMON_OPTIONS);
        // TODO 批量错误
        boolean b = bucket.hasFailures();
        if (b) {
            List<String> collect = Arrays.stream(bucket.getItems()).map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
            log.error("商品{}上架错误",collect);
        }
        return b;

    }
}
