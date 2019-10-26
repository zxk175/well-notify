package com.zxk175.core.common.bean.param.wx;

import com.zxk175.core.common.check.NotBlank;
import com.zxk175.core.common.util.MyStrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxk175
 * @since 2019/05/18 11:20
 */
@Data
public class DeviceNotifyParam {

    @NotBlank(message = "sendKey不可为空")
    @ApiModelProperty(value = "sendKey")
    private String sendKey;

    @NotBlank(message = "标题不可为空")
    @ApiModelProperty(value = "标题，必填。不超过80个字", example = "测试模板消息")
    private String title;

    @ApiModelProperty(value = "长文本内容，选填。", example = "长文本内容")
    private String content;


    public String getContent() {
        if (MyStrUtil.isBlank(content)) {
            return "未填写内容";
        }

        return content;
    }
}
