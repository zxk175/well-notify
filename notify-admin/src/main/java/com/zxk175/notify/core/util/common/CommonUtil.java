package com.zxk175.notify.core.util.common;

import com.google.common.collect.Maps;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.DateUtil;
import com.zxk175.notify.core.util.ExceptionUtil;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.spring.SpringActiveUtil;
import com.zxk175.notify.module.bean.param.PageParam;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author zxk175
 * @since 2020-03-28 14:30
 */
public class CommonUtil {
	
	public static void buildPageParam(PageParam param) {
		param.setPageRaw(param.getPage());
		
		long[] ints = transToStartEnd(param.getPage(), param.getSize());
		param.setPage(ints[0]);
		param.setSize(ints[1]);
	}
	
	private static long[] transToStartEnd(long page, long size) {
		// 最多1次100条数据
		long sizeLimit = 100;
		if (size > sizeLimit) {
			size = sizeLimit;
		}
		
		if (page < 1) {
			page = 1;
		}
		
		if (size < 1) {
			size = 0;
		}
		
		long start = (page - 1) * size;
		long end = size;
		
		return new long[]{start, end};
	}
	
	public static String requestLimitKey(String url, String ip) {
		// 拼接url和ip
		return "req_limit_" + url + ip;
	}
	
	public static String getFileName(String imgUrl) {
		if (MyStrUtil.isNotBlank(imgUrl) && imgUrl.contains("zxk175")) {
			return imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.lastIndexOf('.'));
		}
		
		return "";
	}
	
	public static String buildErrorPath(String prefix) {
		return prefix + MyStrUtil.SLASH + SpringActiveUtil.getActive() + MyStrUtil.SLASH + DateUtil.now(Const.DATE_FORMAT_NO_TIME) + MyStrUtil.SLASH;
	}
	
	public static InputStream createExceptionHtml(String title, Exception ex) throws Exception {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setClassForTemplateLoading(CommonUtil.class, "/template/");
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("title", title);
		String exDetailInfo = ExceptionUtil.getExceptionDetail(ex);
		data.put("ex", exDetailInfo);
		
		Template template = configuration.getTemplate("error.ftl");
		StringWriter out = new StringWriter();
		template.process(data, out);
		return new ByteArrayInputStream(out.toString().getBytes(Const.UTF_8_OBJ));
	}
	
}
