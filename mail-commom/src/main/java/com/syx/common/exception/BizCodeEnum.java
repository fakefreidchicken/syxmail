package com.syx.common.exception;

/**
 * ClassName: BizCodeEnum <br/>
 * Description: 错误码定义规则为5位数字，前两位表示业务场景，后三位表示错误码<br/>
 * 错误码列表：<br/>
 * 10：通用<br/>
 *     001：参数格式校验<br/>
 * 11：商品<br/>
 * 12：订单<br/>
 * 13：购物车<br/>
 * 14：物流<br/>
 * date: 2024/3/16 4:30 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 *
 */
public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    PRODUCT_UP_EXCEPTIOM(11000,"商品上架异常");

    private int code;
    private String msg;
    BizCodeEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
