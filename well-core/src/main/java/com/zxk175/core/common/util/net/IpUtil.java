package com.zxk175.core.common.util.net;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.util.MyStrUtil;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;

/**
 * Ip工具类
 *
 * @author zxk175
 * @since 17/11/1 14:31
 */
public class IpUtil {

    public static String getClientIp(HttpServletRequest request) {
        String unknown = "unknown";
        if (ObjectUtil.isNull(request)) {
            return unknown;
        }

        String ip = request.getHeader("x-forwarded-for");
        boolean flag = MyStrUtil.isBlank(ip);

        if (flag || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (flag || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (flag || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (Const.IPV6_LOCAL.equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(MyStrUtil.COMMA).length > 1) {
            ip = ip.split(StrUtil.COMMA)[0];
        }
        return ip;
    }

    public static String getAddressByIp(String ip) {
        try {
            String fileResourcePath = "ip/ip2region.db";
            ClassPathResource classPathResource = new ClassPathResource(fileResourcePath);
            return getIpRegion(ip, IoUtil.readBytes(classPathResource.getInputStream()));
        } catch (Exception ex) {
            ex.printStackTrace();

            StringBuilder msg = new StringBuilder(16);
            msg.append("exception：").append(ex.toString());
            StackTraceElement[] stackTrace = ex.getStackTrace();
            int length = stackTrace.length;
            for (int i = 0; i < (length < Const.SIX ? length : Const.SIX); i++) {
                msg.append(Const.FORMAT2).append("stackTrace[").append(i).append("]：").append(stackTrace[i]);
            }

            return msg.toString();
        }
    }

    private static String getIpRegion(String ip, byte[] dbBinByte) throws Exception {
        if (Util.isIpAddress(ip)) {
            DbConfig config = new DbConfig();
            MyDbSearcher searcher = new MyDbSearcher(config, dbBinByte);
            DataBlock dataBlock = searcher.memorySearch(ip);
            searcher.close();
            return dataBlock.getRegion();
        }

        throw new RuntimeException("Invalid IP Address：" + ip);
    }

    public static void main(String[] args) {
        System.out.println(getAddressByIp("119.23.35.15"));
    }
}
