package com.zxk175.notify.core.util.spring;

import cn.hutool.core.util.ArrayUtil;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.MyStrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zxk175
 * @since 2020-03-29 13:47
 */
@Slf4j
@Component
@AllArgsConstructor
public class SpringActiveUtil {

    private final Environment environment;
    private static SpringActiveUtil springActiveUtil;


    @PostConstruct
    public void init() {
        springActiveUtil = this;
    }

    public static String getActive() {
        return getActive(springActiveUtil.environment);
    }

    private static String getActive(Environment environment) {
        String[] active = environment.getActiveProfiles();
        if (ArrayUtil.isEmpty(active)) {
            throw new RuntimeException("未获取到运行环境");
        }

        return active[0];
    }

    public static boolean isDebug() {
        String active = getActive();

        return isDebug(active);
    }

    public static boolean isDebug(Environment environment) {
        String active = getActive(environment);

        return isDebug(active);
    }

    private static boolean isDebug(String active) {
        return (MyStrUtil.eq(Const.DEV, active) || MyStrUtil.eq(Const.TEST, active));
    }

    public static String getUrlStr() {
        boolean flag = isDebug();
        if (flag) {
            return "http://localhost:8010/notify";
        }

        return "https://notify.zxk175.com/notify";
    }

    public static String chineseStr() {
        String active = getActive();
        switch (active) {
            case Const.DEV:
                return "开发";
            case Const.TEST:
                return "测试";
            case Const.PROD:
            case Const.DOCKER:
                return "生产";
            default:
                return "未知环境";
        }
    }

}