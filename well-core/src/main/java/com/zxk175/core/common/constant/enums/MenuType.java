package com.zxk175.core.common.constant.enums;

import com.zxk175.core.common.constant.Const;

/**
 * @author zxk175
 * @since 2019/03/28 10:38
 */
public enum MenuType {

    //
    CATALOG(Const.ONE),
    MENU(Const.TWO),
    BUTTON(Const.THREE);


    private Integer value;

    MenuType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }
}
