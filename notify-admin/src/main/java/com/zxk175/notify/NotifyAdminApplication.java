package com.zxk175.notify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@Slf4j
@Controller
@SpringBootApplication
public class NotifyAdminApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NotifyAdminApplication.class, args);
		log.info("项目启动成功");
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/swagger-ui.html";
	}
	
}
