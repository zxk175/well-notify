package com.zxk175.notify.module.bean.param.notify;

import com.zxk175.notify.core.check.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxk175
 * @since 2020-03-29 13:58
 */
@Data
public class NotifyChannelInfoParam {
	
	@NotBlank
	@ApiModelProperty(value = "通道Id", example = "0")
	private String channelId;
	
}
