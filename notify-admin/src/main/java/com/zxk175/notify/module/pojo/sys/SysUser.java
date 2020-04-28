package com.zxk175.notify.module.pojo.sys;

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
 * 系统用户表
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_user")
@ApiModel(value = "SysUser对象", description = "系统用户表")
public class SysUser extends Model<SysUser> {
	
	private static final long serialVersionUID = 1L;
	
	
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键", hidden = true)
	private Long id;
	
	@TableField("user_name")
	@ApiModelProperty(value = "名字", example = "zxk175")
	private String userName;
	
	@TableField("avatar")
	@ApiModelProperty(value = "头像", example = "https://cdn.zxk175.com/img/avatar.png")
	private String avatar;
	
	@TableField("mobile")
	@ApiModelProperty(value = "手机号", example = "18820216402")
	private String mobile;
	
	@TableField("salt")
	@ApiModelProperty(value = "盐值", hidden = true)
	private String salt;
	
	@TableField("password")
	@ApiModelProperty(value = "密码", example = "123456")
	private String password;
	
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间", hidden = true)
	private LocalDateTime createTime;
	
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间", hidden = true)
	private LocalDateTime updateTime;
	
	@TableField("state")
	@ApiModelProperty(value = "状态", hidden = true)
	private Integer state;
	
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
