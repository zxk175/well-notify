package com.zxk175.notify.core.check;

import com.zxk175.notify.core.util.MyStrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zxk175
 * @since 2020-03-29 13:55
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {
	
	@Override
	public void initialize(NotBlank constraintAnnotation) {
		// to do nothing
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return MyStrUtil.isNotBlank(value);
	}
	
}
