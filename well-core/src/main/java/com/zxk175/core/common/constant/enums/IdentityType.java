package com.zxk175.core.common.constant.enums;

import com.zxk175.core.common.constant.Const;

/**
 * @author zxk175
 * @since 2019/04/15 13:27
 */
public enum IdentityType {

    // 1-普通管理员 2-超级管理员
    ORDINARY(Const.ONE),
    SUPER(Const.TWO);


    private Integer value;

    IdentityType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }
}
