package io.github.greenhandsw.payeureka.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultEnum {
    REG_SUCCESS(10000, "注册成功"),
    REG_FAILURE(10001, "注册失败"),
    LOGIN_SUCCESS(20000, "登陆成功"),
    LOGIN_FAILURE(20001, "登陆失败"),
    UNREG_SUCCESS(30000, "注销成功"),
    UNREG_FAILURE(30001, "注销失败"),

    UNKNOWN(99999, "未知错误"),
    ;

    private Integer code;
    private String message;
}
