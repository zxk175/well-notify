package com.zxk175.notify.module.bean.param;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zxk175
 * @since 2020-03-29 13:59
 */
@Data
@Accessors(chain = true)
public class PageParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "第几页", example = "1")
    private Long page;

    @ApiModelProperty(hidden = true)
    private Long pageRaw;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private Long size;


    public Long getPage() {
        return ObjectUtil.isNull(page) ? 0L : (page < 0 ? 0 : page);
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageRaw() {
        return ObjectUtil.isNull(pageRaw) ? 1L : (pageRaw <= 0 ? 1L : pageRaw);
    }

    public Long getSize() {
        return ObjectUtil.isNull(size) ? 10L : size;
    }

}

