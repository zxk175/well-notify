package com.zxk175.notify.config;

import com.zxk175.notify.core.util.wx.WxAccessUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@Slf4j
@Component
@AllArgsConstructor
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private WxAccessUtil wxAccessUtil;


    @Override
    public void onApplicationEvent(@Nullable ContextRefreshedEvent refreshedEvent) {
        try {
            String wxGlobalToken = wxAccessUtil.getGlobalToken();
            log.info("WxGlobalToken：{}", wxGlobalToken);
            log.info("初始化成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("初始化失败");
        }
    }

}
