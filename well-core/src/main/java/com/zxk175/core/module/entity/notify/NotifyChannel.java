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
 * 通道表
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_notify_channel")
@ApiModel(value = "NotifyChannel对象", description = "通道表")
public class NotifyChannel extends Model<NotifyChannel> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "channel_id", type = IdType.ID_WORKER)
    @ApiModelProperty(value = "主键", hidden = true)
    private Long channelId;

    @TableField("channel_name")
    @ApiModelProperty(value = "通道名字", example = "test")
    private String channelName;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    @TableField("state")
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.channelId;
    }
}
