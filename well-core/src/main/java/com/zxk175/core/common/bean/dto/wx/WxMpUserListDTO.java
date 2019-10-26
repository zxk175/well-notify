package com.zxk175.core.common.bean.dto.wx;

import lombok.Data;

@Data
public class WxMpUserListDTO {

    private Long total;

    private Long count;

    private WxMpUserListDataDTO data;

    private String next_openid;
}
