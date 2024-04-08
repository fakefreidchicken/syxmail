package com.syx.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: MemberPrice <br/>
 * Description: <br/>
 * date: 2024/3/24 17:30 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;

}
