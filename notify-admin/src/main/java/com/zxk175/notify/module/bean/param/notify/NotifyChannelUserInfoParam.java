package com.zxk175.notify.module.bean.param.notify;

import com.zxk175.notify.core.check.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxk175
 * @since 2020-03-28 22:11
 */
@Data
public class NotifyChannelUserInfoParam {

    @NotBlank
    @ApiModelProperty(value = "用户Id", example = "0")
    private String userId;

}
