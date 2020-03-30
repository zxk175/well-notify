package com.zxk175.notify.module.bean.param.sys.user;

import com.zxk175.notify.module.bean.param.sys.SysUserBaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zxk175
 * @since 2020-03-28 22:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRemoveParam extends SysUserBaseParam {

    @ApiModelProperty(value = "身份标识", example = "1")
    private Integer identity;

}
