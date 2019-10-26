package com.zxk175.core.common.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zxk175
 * @since 2019/03/23 19:33
 */
@Data
@Accessors(chain = true)
public class PageParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "第几页", example = "0")
    private Integer page;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private Integer size;
}

