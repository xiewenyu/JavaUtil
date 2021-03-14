package com.common.utils;

import com.common.enums.EscapeCharEnum;

import java.io.*;

/**
 * description: IO工具类
 *
 * @author: xiewy
 * @date: Created in 2020/9/18 4:56 下午
 * @version: V1.0
 */
public class IOUtils {

    /**
     * description: <b>读取文件内容 字符输入流：InputStreamReader</b>
     *
     * @param filePath 读取文件路径(字符编码 : utf-8)
     * @return java.lang.String
     * @author xiewy
     * @date 2020/1/20 5:49 下午
     */
    public static String readByInputStreamReaderUTF(String filePath) throws IOException {
        return readByInputStreamReader(new File(filePath), "utf-8");
    }

    /**
     * description: <b>读取文件内容 字符输入流：InputStreamReader</b>
     *
     * @param file        读取文件
     * @param charsetName 读取文件的字符编码 : utf-8/gbk...
     * @return java.lang.String
     * @author xiewy
     * @date 2020/1/20 5:48 下午
     */
    public static String readByInputStreamReader(File file, String charsetName) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), charsetName);
        int len;
        char[] chs = new char[1024];
        StringBuffer contents = new StringBuffer();
        while ((len = isr.read(chs)) != -1) {
            contents.append(new String(chs, 0, len));
        }
        close(isr);
        return contents.toString();
    }

    /**
     * 关闭InputStreamReader流
     *
     * @param isr
     */
    public static void close(InputStreamReader isr) {
        if (isr != null) {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭 OutputStream 流
     *
     * @param os
     */
    public static void close(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭 InputStream 流
     *
     * @param is
     */
    public static void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * description: <b>使用字节输出流 FileOutputStream 写出数据到文件</b>
     *
     * @param file        写出文件
     * @param content     写出内容
     * @param append      数据追加续写 true: 表示追加数据，false: 表示清空原有数据
     * @param charsetName 字符编码
     * @return void
     * @author xiewy
     * @date 2020/1/20 4:59 下午
     */
    public static void writeFileByFileOutputStream(File file, String content, Boolean append, String charsetName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, append);
        //fileOutputStream.write("\r".getBytes());
        fileOutputStream.write(content.getBytes(charsetName));
        fileOutputStream.write(EscapeCharEnum.MAC_NEWLINE.toString().getBytes());
        close(fileOutputStream);
    }

    /**
     * description: <b>字节输入流 FileInputStream 读取文件内容</b>
     *
     * @param file 读取文件
     * @return java.lang.String 文件内容
     * @author xiewy
     * @date 2020/1/20 4:49 下午
     */
    public static String readFileByInputStream(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        int len;
        byte[] bytes = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        while ((len = fileInputStream.read(bytes)) != -1) {
            stringBuffer.append(new String(bytes, 0, len));
        }
        close(fileInputStream);
        return stringBuffer.toString();
    }
}
