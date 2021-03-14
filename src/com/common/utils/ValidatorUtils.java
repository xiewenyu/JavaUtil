package com.common.utils;

import com.ai.acctcomp.base.util.StringUtil;
import com.ailk.common.data.IData;

/**
 * description: 校验工具类
 *
 * @author: xiewy
 * @date: Created in 2020/9/18 5:00 下午
 * @version: V1.0
 */
public class ValidatorUtils {
    /**
     * description: <b>判断数组是否为非空</b>
     *
     * @param array
     * @return true：非空数组  false：空数组
     * @author xiewy
     * @date 2020/7/30 11:26 上午
     */
    public static boolean isNonBlank(String[] array) {
        return !isBlank(array);
    }

    /**
     * description: <b>判断数组是否为空</b>
     *
     * @param array
     * @return true：空数组  false：非空数组
     * @author xiewy
     * @date 2020/7/30 11:27 上午
     */
    public static boolean isBlank(String[] array) {
        return array != null && array.length > 0 ? false : true;
    }

    /**
     * 字符串是否不为空
     *
     * @param value
     * @return
     */
    public static boolean isNonBlank(String value) {
        return !isBlank(value);
    }

    /**
     * 字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return StringUtil.isBlank(value);
    }

    /**
     * <b><p>true: 非空</p></b>
     * <b><p>false:空</p></b>
     *
     * @param iData
     * @return boolean
     * @author xiewy
     * @date 2020/3/30 11:42 上午
     */
    public static boolean isNonNull(IData iData) throws Exception {
        return !isNull(iData);
    }

    /**
     * <b><p>true: 空</p></b>
     * <b><p>false:非空</p></b>
     *
     * @param iData
     * @return boolean
     * @author xiewy
     * @date 2020/4/27 4:50 下午
     */
    public static boolean isNull(IData iData) throws Exception {
        boolean status = true;
        try {
            if (iData != null && iData.size() > 0) {
                status = false;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return status;
    }

}
