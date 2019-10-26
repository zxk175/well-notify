package com.zxk175.core.module.entity.notify;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2019-05-18 12:10:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_notify_msg")
@ApiModel(value = "NotifyMsg对象", description = "消息表")
public class NotifyMsg extends Model<NotifyMsg> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "msg_id", type = IdType.ID_WORKER)
    @ApiModelProperty(value = "主键", hidden = true)
    private Long msgId;

    @TableField("title")
    @ApiModelProperty(value = "标题", example = "test")
    private String title;

    @TableField("content")
    @ApiModelProperty(value = "内容", example = "test")
    private String content;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @TableField("state")
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.msgId;
    }
}
