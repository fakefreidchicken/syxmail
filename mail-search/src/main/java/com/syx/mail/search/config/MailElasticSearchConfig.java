package com.syx.mail.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: MailElasticSearchConfig <br/>
 * Description: <br/>
 * date: 2024/4/1 3:58 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Configuration
public class MailElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.25.19", 9200, "http")));
        return client;
    }
}
