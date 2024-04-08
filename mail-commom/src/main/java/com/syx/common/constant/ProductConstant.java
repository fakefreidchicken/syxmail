package com.syx.common.constant;

import lombok.Data;

/**
 * ClassName: ProductConstant <br/>
 * Description: <br/>
 * date: 2024/3/17 16:10 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
public class ProductConstant {
    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(0, "销售属性");
        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }

    public enum StatusEnum {
        NEW_SPU(0, "新建"), SPU_UP(1, "商品上架"),SPU_DOWN(2,"商品下架");
        private int code;
        private String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }
}
