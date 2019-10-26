package com.zxk175.notify.module.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zxk175
 * @since 2019-10-12 16:29
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/notify/msg")
    public String msg() {
        return "msg";
    }
}
