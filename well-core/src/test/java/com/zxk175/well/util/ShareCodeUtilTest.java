package com.zxk175.well.util;

import com.zxk175.core.common.util.ShareCodeUtil;
import com.zxk175.core.common.util.id.DbIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zxk175
 * @since 2019/04/17 17:25
 */
@Slf4j
public class ShareCodeUtilTest {

    @Test
    public void genShareCode() {
        Set<Long> idSet = new HashSet<>();
        int maxTimes = 1000;
        for (int i = 0; i < maxTimes; i++) {
            idSet.add(DbIdUtil.id());
        }

        if (maxTimes == idSet.size()) {
            log.info("无重复Id");
        } else {
            log.info("有重复Id");
        }

        Set<String> codeSet = new HashSet<>();
        for (Long id : idSet) {
            String code = ShareCodeUtil.code(id);
            codeSet.add(code);
            log.info(code);
            log.info(ShareCodeUtil.id2Str(code));
        }

        if (maxTimes == codeSet.size()) {
            log.info("无重复Code");
        } else {
            log.info("有重复Code");
        }
    }
}
