package com.common.utils;

import com.ailk.common.data.IData;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * description: 打印工具类
 *
 * @author: xiewy
 * @date: Created in 2020/9/18 5:05 下午
 * @version: V1.0
 */
public class PrintUtils {
    public static void printlns(String printContent) {
        if (ValidatorUtils.isNonBlank(printContent)) {
            println(printContent);
            println("");
            if (printContent.contains("\',\'")) {
                println("共有：" + printContent.split("\',\'").length + " 个");
            } else {
                println("共有：" + printContent.split(",").length + " 个");
            }
            println("====================================================================================================================================================================");
        }
    }

    public static void println(String printContent) {
        System.out.println(printContent);
    }

    public static void println(IData printContent) {
        Set<String> keySets = printContent.keySet();
        for (String keySet : keySets) {
            String value = printContent.getString(keySet);
            println(keySet + " = " + value);
        }
    }
    public static void println(String[] printContent) {
        for (String content : printContent) {
            println(content);
        }
    }

    public static void println(int printContent) {
        System.out.println(printContent);
    }

    public static void println(List<String> printContent) {
        for (String content : printContent) {
            println(content);
        }
    }

    public static void println(File[] printContent) {
        for (File content : printContent) {
            if ( content != null && content.length() > 0) {
                println(content.getPath());
            }
        }
    }
}
