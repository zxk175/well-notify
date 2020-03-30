package com.zxk175.notify.module.pojo.wx;

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
 * 微信用户表
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_wx_user")
@ApiModel(value = "WxUser对象", description = "微信用户表")
public class WxUser extends Model<WxUser> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键", hidden = true)
    private Long id;

    @TableField("open_id")
    @ApiModelProperty(value = "openId", example = "test")
    private String openId;

    @TableField("union_id")
    @ApiModelProperty(value = "unionId", example = "test")
    private String unionId;

    @TableField("gender")
    @ApiModelProperty(value = "用户昵称")
    private Boolean gender;

    @TableField("country")
    @ApiModelProperty(value = "所在国家", example = "test")
    private String country;

    @TableField("province")
    @ApiModelProperty(value = "省份", example = "test")
    private String province;

    @TableField("city")
    @ApiModelProperty(value = "城市", example = "test")
    private String city;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    @TableField("state")
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer state;

    @TableField("nick_name")
    @ApiModelProperty(value = "用户头像", example = "test")
    private String nickName;

    @TableField("avatar")
    @ApiModelProperty(value = "用户头像", example = "test")
    private String avatar;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
