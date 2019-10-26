package com.zxk175.core.common.bean.param.sys.menu;

import com.zxk175.core.common.bean.param.sys.SysUserBaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zxk175
 * @since 2019/04/15 12:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuListParam extends SysUserBaseParam {

    private Integer menuType;
}
