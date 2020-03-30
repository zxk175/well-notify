package com.zxk175.notify.core.check;

import com.zxk175.notify.core.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zxk175
 * @since 2020-03-29 13:52
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
        // to do nothing
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return RegexUtil.isMobile(value);
    }

}

