package com.common.utils;

import java.util.Random;

/**
 * description: 通用工具类
 *
 * @author: xiewy
 * @date: Created in 2020/10/10 2:23 下午
 * @version: V1.0
 */
public class CommonUtils {
    private static final Random random = new Random();
    private static final String[] numArr = {"1", "5", "0", "3", "8", "9", "4", "6", "2", "7"};
    private static final String[] lowerCaseArr = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
    private static final String[] upperCaseArr = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
    private static final String[] specialCharArr = {"~", "!", "@", "#", "$", "%", "^", "&", "*"};

    /**
     * description: <b>获取指定长度的随机字符串 (数字 + 大小写字母)</b>
     *
     * @param length 字符串长度
     * @return java.lang.String
     * @author xiewy
     * @date 2020/10/10 3:11 下午
     */
    public static String getRandomString(int length) throws Exception {
        if (length <= 0) {
            throw new Exception("字符串长度必须大于 0 ！");
        }
        String randomStr = "";
        for (int i = 0; i < length; i++) {
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    randomStr += getNumber(1);
                    break;
                case 1:
                    randomStr += getLowerCase(1);
                    break;
                case 2:
                    randomStr += getUpperCase(1);
                    break;
                //case 3:
                //    randomStr += getSpecialChar(1);
                //    break;
                default:
                    break;
            }
        }
        return randomStr;
    }

    /**
     * description: <b>得到长度为 length 的特殊字符字符串</b>
     *
     * @param length 长度
     * @return java.lang.String
     * @author xiewy
     * @date 2020/10/10 3:07 下午
     */
    private static String getSpecialChar(int length) {
        String specialCharStrs = "";
        for (int i = 0; i < length; i++) {
            int specialChar = random.nextInt(9);
            specialCharStrs += specialCharArr[specialChar];
        }
        return specialCharStrs;
    }

    /**
     * description: <b>得到长度为 length 的大写字母字符串</b>
     *
     * @param length 长度
     * @return java.lang.String
     * @author xiewy
     * @date 2020/10/10 3:03 下午
     */
    private static String getUpperCase(int length) {
        String upperCaseStrs = "";
        for (int i = 0; i < length; i++) {
            int upperCase = random.nextInt(26);
            upperCaseStrs += upperCaseArr[upperCase];
        }
        return upperCaseStrs;
    }

    /**
     * description: <b>得到长度为 length 的小写字母字符串</b>
     *
     * @param length 长度
     * @return java.lang.String
     * @author xiewy
     * @date 2020/10/10 3:01 下午
     */
    private static String getLowerCase(int length) {
        String lowerCaseStrs = "";
        for (int i = 0; i < length; i++) {
            int lowerCase = random.nextInt(26);
            lowerCaseStrs += lowerCaseArr[lowerCase];
        }
        return lowerCaseStrs;
    }

    /**
     * description: <b>得到长度为 length 的纯数字字符串（不包括正负和小数点等符号）</b>
     *
     * @param length 长度
     * @return java.lang.String
     * @author xiewy
     * @date 2020/10/10 2:52 下午
     */
    private static String getNumber(int length) {
        String numStrs = "";
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(10);
            numStrs += numArr[num];
        }
        return numStrs;
    }
}
