package com.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * description:
 *
 * @author: xiewy
 * @date: Created in 2020/11/9 10:56 上午
 * @version:
 */
public class DoubleUtils {

    /**
     * description: <b>double 比较大小</b>
     * @param val1
     * @param val2
     * @return int <p> 1   : val1 > val2  </p>
     *             <p>-1 : val1 < val2  </p>
     *             <p> 0   : val1 = val2  </p>
     * @author xiewy
     * @date 2020/11/9 9:52 上午
     */
    public static int compareToDouble(double val1, double val2) {
        BigDecimal bigDecimal1 = new BigDecimal(val1);
        BigDecimal bigDecimal2 = new BigDecimal(val2);
        return bigDecimal1.compareTo(bigDecimal2);
    }

    /**
     * description: <b>double 数字是否大于 0 </b>
     * @param val
     * @return int  <p> 1   : val1 > val2  </p>
     *              <p>-1 : val1 < val2  </p>
     *              <p> 0   : val1 = val2  </p>
     * @author xiewy
     * @date 2020/11/9 11:00 上午
     */
    public static int comparaToZero(double val) {
        return compareToDouble(val, 0);
    }

    /**
     * description: <b>保留小数</b>
     * @param amount 指定数字
     * @param format 返回结果是否保留两位小数
     * @param digits 保留小数点后几位数
     * @return java.lang.String
     * @author xiewy
     * @date 2020/11/25 5:38 下午
     */
    public static String keepDecimals(String amount, boolean format, int digits) throws Exception{
        String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        int val = 1;
        for (int i = 0; i < digits; i++) {
            val = val * 10;
        }
        String fee = BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(val)).toString();
        if(format){
            BigDecimal calcAmount = new BigDecimal(fee);
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(calcAmount);
        }
        return fee;
    }
}
