package com.zxk175.core.common.bean.param.notify;

import com.zxk175.core.common.check.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxk175
 * @since 2019-10-24 11:17
 */
@Data
public class NotifyChannelInfoParam {

    @NotBlank
    @ApiModelProperty(value = "通道Id", example = "0")
    private String channelId;
}
