package com.syx.mail.search.vo;

import com.syx.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * ClassName: SearchResult <br/>
 * Description: <br/>
 * date: 2024/4/7 21:05 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Data
public class SearchResult {

    private List<SkuEsModel> products;
    private Integer pageNum;
    private Long total;
    private Integer totalPages;
    private List<BrandVo> brands;
    private List<AttrVo> attrs;
    private List<CatalogVo> catalogs;

    @Data
    public static class BrandVo {
        private Long brandId;
        private String BrandName;
        private String brandImg;
    }
    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }

    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }
}
