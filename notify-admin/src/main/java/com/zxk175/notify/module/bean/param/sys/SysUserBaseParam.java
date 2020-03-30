package com.zxk175.notify.module.bean.param.sys;

import com.zxk175.notify.core.check.NotBlank;
import com.zxk175.notify.core.constant.Const;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxk175
 * @since 2020-03-28 22:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserBaseParam {

    @NotBlank(message = "{user.id.not.blank}")
    @ApiModelProperty(value = "用户Id", example = Const.DEFAULT_UID)
    private String userId;

}
