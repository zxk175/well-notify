package com.zxk175.core.common.bean.param.sys.user;

import cn.hutool.core.util.ObjectUtil;
import com.zxk175.core.common.bean.param.sys.SysUserBaseParam;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.constant.enums.MenuType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zxk175
 * @since 2019/03/23 20:53
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserPermsParam extends SysUserBaseParam {

    private boolean hasSupper;

    @ApiModelProperty(value = "菜单类型", example = Const.DEFAULT_MOBILE)
    private Integer menuType;


    public SysUserPermsParam(String userId) {
        super(userId);
        this.menuType = MenuType.BUTTON.value();
    }

    public boolean hasSupper() {
        return hasSupper;
    }

    public Integer getMenuType() {
        return ObjectUtil.isNull(menuType) ? MenuType.BUTTON.value() : menuType;
    }
}
