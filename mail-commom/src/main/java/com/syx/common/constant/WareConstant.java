package com.syx.common.constant;

/**
 * ClassName: WareConstant <br/>
 * Description: <br/>
 * date: 2024/3/30 4:58 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
public class WareConstant {
    public enum PurchaseStatusEnum {
        CREATE(0, "新建"), ASSIGNED(1, "已分配"),
        RECEIVED(2, "已领取"),FINISHED(3, "已完成"),HASERROR(4, "有异常");
        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
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

    public enum PurchaseDetailStatusEnum {
        CREATE(0, "新建"), ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"),FINISHED(3, "已完成"),HASERROR(4, "采购失败");
        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code, String msg) {
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
