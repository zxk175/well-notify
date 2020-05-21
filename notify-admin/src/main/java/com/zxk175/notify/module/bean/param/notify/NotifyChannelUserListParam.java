package com.zxk175.notify.module.bean.param.notify;

import com.zxk175.notify.module.bean.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zxk175
 * @since 2020-03-28 22:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyChannelUserListParam extends PageParam {

    @ApiModelProperty(value = "通道Id", example = "999")
    private String channelId;

}
