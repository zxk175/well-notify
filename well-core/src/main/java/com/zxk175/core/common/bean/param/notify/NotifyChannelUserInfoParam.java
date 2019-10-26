package com.zxk175.core.common.bean.param.notify;

import com.zxk175.core.common.check.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NotifyChannelUserInfoParam {

    @NotBlank
    @ApiModelProperty(value = "用户Id", example = "0")
    private String userId;
}
