package com.zxk175.notify.core.constant.enums;

import com.zxk175.notify.core.constant.Const;

/**
 * @author zxk175
 * @since 2020-03-29 13:51
 */
public enum StateType {

    // 1-显示 0-隐藏
    SHOW(Const.ONE),
    HIDE(Const.ZERO);


    private final Integer value;

    StateType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

}
