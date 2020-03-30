package com.zxk175.notify.module.pojo.notify;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_notify_msg")
@ApiModel(value = "NotifyMsg对象", description = "消息表")
public class NotifyMsg extends Model<NotifyMsg> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键", hidden = true)
    private Long id;

    @TableField("title")
    @ApiModelProperty(value = "标题", example = "test")
    private String title;

    @TableField("content")
    @ApiModelProperty(value = "内容", example = "test")
    private String content;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @TableField("state")
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
