package com.zxk175.notify.module.bean.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zxk175
 * @since 2020-03-29 13:59
 */
@Data
@Accessors(chain = true)
public class ErrorVo implements Serializable {

    private String field;

    private String message;

    private Object rejectedValue;

    @JSONField(serialize = false)
    private Integer index;

}
