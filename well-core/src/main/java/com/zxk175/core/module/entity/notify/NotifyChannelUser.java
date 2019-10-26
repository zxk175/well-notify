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
 * 通道用户表
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_notify_channel_user")
@ApiModel(value = "NotifyChannelUser对象", description = "通道用户表")
public class NotifyChannelUser extends Model<NotifyChannelUser> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "user_id", type = IdType.ID_WORKER)
    @ApiModelProperty(value = "主键", hidden = true)
    private Long userId;

    @TableField("channel_id")
    @ApiModelProperty(value = "通道Id", example = "0")
    private Long channelId;

    @TableField("full_name")
    @ApiModelProperty(value = "名字", example = "test")
    private String fullName;

    @TableField("open_id")
    @ApiModelProperty(value = "openId", example = "test")
    private String openId;

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
        return this.userId;
    }
}
