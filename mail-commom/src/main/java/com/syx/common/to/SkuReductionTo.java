package com.syx.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName: SkuReductionTo <br/>
 * Description: <br/>
 * date: 2024/3/26 22:34 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Data
public class SkuReductionTo {

    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
