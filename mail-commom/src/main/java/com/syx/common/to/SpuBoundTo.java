package com.syx.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: SpuBoundTo <br/>
 * Description: <br/>
 * date: 2024/3/26 22:22 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
