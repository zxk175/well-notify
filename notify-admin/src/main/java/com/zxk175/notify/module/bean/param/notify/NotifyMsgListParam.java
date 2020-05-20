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
public class NotifyMsgListParam extends PageParam {

    @ApiModelProperty(value = "标题", example = "异常")
    private String title;

    @ApiModelProperty(value = "是否发送 0-未发 1-已发", example = "1")
    private Integer sendFlag;

    @ApiModelProperty(value = "通道Id", example = "1174160779906125825")
    private String channelId;

    @ApiModelProperty(value = "创建时间", example = "2020-05-09")
    private String createTime;

}
