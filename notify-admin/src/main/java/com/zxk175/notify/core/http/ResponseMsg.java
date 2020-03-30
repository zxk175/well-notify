package com.zxk175.notify.core.http;

import com.google.common.collect.Lists;
import com.zxk175.notify.core.constant.Const;

import java.util.List;

/**
 * @author zxk175
 * @since 2020-03-29 13:50
 */
public enum ResponseMsg {

    // 请求状态
    SUCCESS(Const.SUCCESS_CODE, Const.SUCCESS_TXT),
    FAILURE(Const.FAILURE_CODE, Const.FAILURE_TXT),
    NOT_FOUND_DATA(2, "暂无数据"),

    // 参数错误 1000-1999
    PARAM_NOT_COMPLETE(1000, "参数缺失"),
    TOKEN_NOT_ERROR(1001, "请在请求头添加登录状态"),
    TOKEN_TIMEOUT_ERROR(1002, "登录状态已失效，请重新登录"),
    TOKEN_FORMAT_ERROR(1003, "登录状态格式错误"),
    TOKEN_CHECK_ERROR(1004, "您的手机号已在其它设备登录，请重新登录"),

    USER_NOT_LOGIN(1005, "用户未登录"),
    USER_LOGIN_ERR(1006, "用户不存在或密码错误"),
    USER_FORBIDDEN(1007, "用户已被禁用"),
    USER_NOT_EXIST(1008, "用户不存在"),

    // 系统相关 4000-4999
    DB_SAVE_SUCCESS(4000, "添加成功"),
    DB_SAVE_FAILURE(4001, "添加失败"),
    DB_DELETE_SUCCESS(4002, "删除成功"),
    DB_DELETE_FAILURE(4004, "删除失败"),
    DB_MODIFY_SUCCESS(4005, "修改成功"),
    DB_MODIFY_FAILURE(4006, "修改失败"),

    // 系统错误 5000-5999
    SYSTEM_INNER_ERROR(5000, "系统繁忙，请稍后重试"),
    ;


    private Integer code;
    private String msg;


    ResponseMsg(Integer code, String msg) {
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
        ResponseMsg[] responseMsgArr = ResponseMsg.values();
        List<Integer> codes = Lists.newArrayList();
        for (ResponseMsg responseMsg : responseMsgArr) {
            if (codes.contains(responseMsg.code)) {
                System.out.println("重复的code值：" + responseMsg.code);
            } else {
                codes.add(responseMsg.code);
            }
        }
    }

}
