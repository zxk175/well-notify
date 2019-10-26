package com.zxk175.notify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.zxk175.notify", "com.zxk175.core"})
public class WellAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WellAdminApplication.class, args);
        log.info("项目启动成功!");
    }
}
