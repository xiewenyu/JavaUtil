package com.common.utils;

/**
 * description:
 *
 * @author: xiewy
 * @date: Created in 2020/10/1 8:41 上午
 * @version:
 */
public class StringUtils {
    /**
     * 字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return value == null || value == "" || value.trim().length() == 0;
    }
}
