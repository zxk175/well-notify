package com.zxk175.notify.core.util.wx;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.MyStrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author zxk175
 * @since 2020-03-29 13:42
 */
@Slf4j
public class WxSignUtil {

    public static String checkSign(HttpServletRequest request, String token) {
        String nonce = request.getParameter("nonce");
        String echoStr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");

        String[] strArray = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(strArray);

        StringBuilder content = new StringBuilder();
        for (String str : strArray) {
            content.append(str);
        }

        String sha1Str = sha1(content.toString());

        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        boolean flag = MyStrUtil.eqIgnoreCase(sha1Str, signature);
        if (flag) {
            return echoStr;
        }

        return "";
    }

    private static String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digest.digest(str.getBytes(Const.UTF_8_OBJ));
            return toHex(bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    private static String toHex(byte[] bytes) {
        StringBuilder content = new StringBuilder();
        for (byte b : bytes) {
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            char[] temp = new char[2];
            temp[0] = chars[(b >>> 4) & 0x0F];
            temp[1] = chars[b & 0x0F];
            content.append(temp);
        }

        return content.toString();
    }

}
