package com.zxk175.core.common.constant.enums;

import com.zxk175.core.common.constant.Const;

public enum StateType {

    // 1=xian
    SHOW(Const.ONE),
    HIDE(Const.ZERO);


    private Integer value;

    StateType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }
}
