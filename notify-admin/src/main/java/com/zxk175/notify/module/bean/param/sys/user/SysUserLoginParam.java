package com.zxk175.notify.module.bean.param.sys.user;

import com.zxk175.notify.core.check.Mobile;
import com.zxk175.notify.core.constant.Const;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxk175
 * @since 2020-03-28 22:13
 */
@Data
public class SysUserLoginParam {

    @Mobile
    @ApiModelProperty(value = "手机号", example = Const.DEFAULT_MOBILE)
    private String mobile;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

}
