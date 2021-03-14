package com.common.enums;

/**
 * 转义字符
 *
 * @author xiewy
 * @version 1.0
 * @date 2019/12/24
 */
public enum EscapeCharEnum {
    /**
     * 换行
     */
    NEWLINE("\n"),
    /**
     * 换行
     */
    WINDOWS_NEWLINE("\r\n"),
    /**
     * 换行
     */
    MAC_NEWLINE("\n"),
    /**
     * 换行
     */
    LINUX_NEWLINE("\n"),
    /**
     * 跳到下一个TAB位置
     */
    TABLE("\t"),
    /**
     * 反斜线字符 \
     */
    BACKSLASH("\\"),
    /**
     * 一个单引号（撇号）字符
     */
    APOSTROPHE("'"),
    /**
     * 一个双引号字符
     */
    DOUBLE_QUOTES("\""),
    /**
     * 空字符(NULL)
     */
    Null("\0"),
    /**
     * CarriageReturn
     */
    CARRIAGERETURN("\r");

    private String escapes;

    EscapeCharEnum(String escapes) {
        this.escapes = escapes;
    }

    EscapeCharEnum() {
    }

    public String getEscapes() {
        return escapes;
    }

    public void setEscapes(String escapes) {
        this.escapes = escapes;
    }

    @Override
    public String toString() {
        return this.escapes;
    }

    public static String getEscapes(String escapes) {
        return EscapeCharEnum.valueOf(escapes).toString();
    }
}