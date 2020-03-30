package com.zxk175.notify.module.controller.common;

import com.zxk175.notify.core.annotation.request.RequestLimit;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.util.wx.WxAccessUtil;
import com.zxk175.notify.module.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zxk175
 * @since 2019-10-12 16:29
 */
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(Const.BASE_URL)
@Api(tags = "公共接口")
public class CommonController extends BaseController {

    private WxAccessUtil wxAccessUtil;


    @ResponseBody
    @RequestLimit(count = Const.LIMIT_30)
    @GetMapping(value = "/wx_global_token/v1")
    @ApiOperation(value = "获取公众号GlobalToken", notes = "获取公众号GlobalToken")
    public Response<Object> getWxGlobalTokenV1() {
        String accessToken = wxAccessUtil.getGlobalToken();

        return ok(accessToken);
    }

}
