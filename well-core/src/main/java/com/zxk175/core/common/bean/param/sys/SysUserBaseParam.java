package com.zxk175.core.common.bean.param.sys;

import com.zxk175.core.common.check.NotBlank;
import com.zxk175.core.common.constant.Const;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxk175
 * @since 2019/03/23 20:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserBaseParam {

    @NotBlank(message = "{user.id.not.blank}")
    @ApiModelProperty(value = "用户Id", example = Const.DEFAULT_UID)
    private String userId;
}
