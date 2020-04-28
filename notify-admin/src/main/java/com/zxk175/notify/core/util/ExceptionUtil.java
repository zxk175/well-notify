package com.zxk175.notify.core.util;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.json.JsonFormatUtil;
import com.zxk175.notify.core.util.message.PushWellUtil;
import com.zxk175.notify.core.util.net.IpUtil;
import com.zxk175.notify.core.util.net.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author zxk175
 * @since 2020-04-01 13:49
 */
public class ExceptionUtil {
	
	private static final String FORMAT1 = Const.FORMAT1;
	private static final String FORMAT2 = Const.FORMAT2;
	private static final String FORMAT4 = Const.FORMAT4;
	private static final String FORMAT5 = Const.FORMAT5;
	private static final String FORMAT_DEFAULT = Const.DATE_FORMAT_CN;
	
	
	public static String getExceptionDetail(Exception e) {
		String result = "";
		if (ObjectUtil.isNotNull(e)) {
			FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
			PrintStream printStream = new PrintStream(outputStream);
			e.printStackTrace(printStream);
			result = outputStream.toString(Const.UTF_8_OBJ);
			printStream.close();
			outputStream.close();
		}
		
		return result;
	}
	
	public static void sendRequestInfo(String title, StringBuilder sb) {
		StringBuilder msg = new StringBuilder(2048);
		
		String now = DateUtil.now(FORMAT_DEFAULT);
		msg.append(now);
		msg.append(sb);
		msg.append(sendRequestInfo());
		
		PushWellUtil.sendNotify(title, msg.toString());
	}
	
	private static StringBuilder sendRequestInfo() {
		StringBuilder msg = new StringBuilder(2048);
		
		HttpServletRequest request = RequestUtil.request();
		if (ObjectUtil.isNull(request)) {
			msg.append(FORMAT2);
			msg.append("未获取到请求信息");
		} else {
			String ip = IpUtil.getClientIp(request);
			msg.append(FORMAT1);
			msg.append("请求者IP地址");
			msg.append(FORMAT2);
			msg.append(ip);
			msg.append(FORMAT1);
			msg.append("请求者IP地域信息");
			msg.append(FORMAT2);
			msg.append(IpUtil.getAddressByIp(ip));
			msg.append(FORMAT1);
			msg.append("请求地址");
			msg.append(FORMAT2);
			String method = request.getMethod();
			String url = RequestUtil.requestUrl(request);
			msg.append(method).append("：").append(url);
			msg.append(FORMAT1);
			msg.append("Head参数");
			msg.append(FORMAT2);
			Map<String, String> headersInfo = RequestUtil.headers(request);
			int headCount = 1;
			int headSize = headersInfo.size();
			for (Map.Entry<String, String> entries : headersInfo.entrySet()) {
				msg.append(entries.getKey()).append("：").append(entries.getValue());
				if (headCount < headSize) {
					msg.append(FORMAT2);
				}
				
				++headCount;
			}
			
			String body;
			msg.append(FORMAT1);
			msg.append("请求参数");
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
				body = IoUtil.read(reader);
				
				handleBody(msg, request, body);
			} catch (Exception ex) {
				msg.append(FORMAT2).append("exception：").append(ex.toString());
				msg.append(FORMAT2).append("stackTrace[0]：").append(ex.getStackTrace()[0]);
				msg.append(FORMAT2).append("stackTrace[1]：").append(ex.getStackTrace()[1]);
			}
		}
		
		return msg;
	}
	
	private static void handleBody(StringBuilder msg, HttpServletRequest request, String body) throws IOException, ServletException {
		if (MyStrUtil.isNotBlank(body)) {
			String contentType = request.getContentType();
			String multipart = "multipart";
			if (contentType.contains(multipart)) {
				msg.append(FORMAT2);
				final Collection<Part> parts = request.getParts();
				for (Part part : parts) {
					Collection<String> headerNames = part.getHeaderNames();
					for (String name : headerNames) {
						Collection<String> headers = part.getHeaders(name);
						// fix 中文乱码
						String head = new String(headers.toString().getBytes(Const.ISO_8859_1), Const.UTF_8_OBJ);
						msg.append(name).append("：").append(head).append(FORMAT2);
					}
				}
			}
			
			String xml = "xml";
			if (contentType.contains(xml)) {
				Map<String, Object> result = XmlUtil.xmlToMap(body);
				body = JSON.toJSONString(result, true);
			}
			
			String json = "json";
			if (contentType.contains(json)) {
				body = JsonFormatUtil.formatJsonStr(body);
			}
			
			if (contentType.contains(json)) {
				msg.append(FORMAT4).append(body).append(FORMAT5);
			}
		} else {
			msg.append(FORMAT2).append("参数为空");
		}
	}
}
