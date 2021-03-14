package com.common.utils;

import com.ai.acctcomp.base.util.StringUtil;
import com.common.enums.RegularEnums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: 正则表达式工具类
 *
 * @author: xiewy
 * @date: Created in 2020/9/18 5:03 下午
 * @version: V1.0
 */
public class RegularUtils {
    /**
     * 对字符串进行小数校验（整数或者小数，包含负数,小数点不能开头或者结尾）
     *
     * @param input 校验字符串
     * @return 在字符串匹配给定的正则表达式时，返回 true ， 否则返回 false
     */
    public static Boolean numberCheck(CharSequence input) {
        if (StringUtil.isBlank(String.valueOf(input))) {
            return false;
        } else {
            return matches(String.valueOf(RegularEnums.NUMBER), input);
        }
    }

    /**
     * 用于检测字符串是否匹配给定的正则表达式
     *
     * @param regex 正则表达式
     * @param input 校验字符串
     * @return 在字符串匹配给定的正则表达式时，返回 true ， 否则返回 false
     */
    public static Boolean matches(String regex, CharSequence input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
