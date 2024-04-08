package com.syx.common.to;

import lombok.Data;

/**
 * ClassName: SkuHasStockVo <br/>
 * Description: <br/>
 * date: 2024/4/2 3:35 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Data
public class SkuHasStockVo {
    private Long skuId;
    private Boolean hasStock;
}
