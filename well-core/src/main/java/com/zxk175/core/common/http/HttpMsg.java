package com.zxk175.core.common.http;

import com.google.common.collect.Lists;
import com.zxk175.core.common.constant.Const;

import java.util.List;

/**
 * @author zxk175
 * @since 2019/03/23 11:34
 */
public enum HttpMsg {

    // 请求状态
    OK(Const.OK_CODE, Const.OK_TXT),
    FAIL(Const.FAIL_CODE, Const.FAIL_TXT),
    NOT_FOUND_DATA(2, "暂无数据"),

    // 参数错误 1000-1999
    PARAM_NOT_COMPLETE(1000, "参数缺失"),
    TOKEN_NOT_ERROR(1001, "请在请求头添加登录状态"),
    TOKEN_TIMEOUT_ERROR(1002, "登录状态已失效，请重新登录"),
    TOKEN_FORMAT_ERROR(1003, "登录状态格式错误"),
    TOKEN_CHECK_ERROR(1004, "您的手机号已在其它设备登录，请重新登录"),

    // 用户错误 2000-2999
    USER_NOT_LOGIN(2000, "用户未登录"),
    USER_LOGIN_ERR(2001, "账号不存在或密码错误"),
    USER_FORBIDDEN(2002, "账号已被禁用"),
    USER_NOT_EXIST(2003, "用户不存在"),

    // 业务错误 3000-3999
    BUSINESS_INNER_ERROR(3000, "业务异常"),

    // 系统相关 4000-4999
    DB_ADD_SUCCESS(4000, "添加成功"),
    DB_DELETE_SUCCESS(4001, "删除成功"),
    DB_MODIFY_SUCCESS(4002, "修改成功"),

    // 系统错误 5000-5999
    SYSTEM_INNER_ERROR(5000, "系统繁忙，请稍后重试"),
    ;


    private Integer code;
    private String msg;


    HttpMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    /**
     * 校验重复的code值
     *
     * @param args ignore
     */
    public static void main(String[] args) {
        HttpMsg[] httpMsgArr = HttpMsg.values();
        List<Integer> codes = Lists.newArrayList();
        for (HttpMsg httpMsg : httpMsgArr) {
            if (codes.contains(httpMsg.code)) {
                System.out.println("重复的code值：" + httpMsg.code);
            } else {
                codes.add(httpMsg.code);
            }
        }
    }
}
