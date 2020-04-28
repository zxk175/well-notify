package com.zxk175.notify.module.bean.param.sys.user;

import com.zxk175.notify.core.constant.Const;
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
public class SysUserListParam extends PageParam {
	
	@ApiModelProperty(value = "用户Id", hidden = true)
	private String userId;
	
	@ApiModelProperty(value = "手机号", example = Const.DEFAULT_MOBILE)
	private String mobile;
	
}
