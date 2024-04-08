package com.syx.mail.search.vo;

import lombok.Data;

import java.util.List;

/**
 * ClassName: SearchParam <br/>
 * Description: <br/>
 * date: 2024/4/7 20:44 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Data
public class SearchParam {
    private String keyword;
    private Long catalog3Id;
    private String sort;
    private Integer hasStock;
    private String skuPrice;
    private List<Long> brandId;
    private List<String> attrs;
    private Integer pageNum;

}
