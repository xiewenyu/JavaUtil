package com.common.utils;

import com.ailk.common.data.IData;
import com.ailk.common.data.impl.DataMap;
import com.common.enums.EscapeCharEnum;

/**
 * description: 参数配置工具类
 * 
 * @author: xiewy
 * @date: Created in 2020/9/18 4:55 下午
 * @version: V1.0
 */
public class ParamUtils {

    public static void accountParamConf() throws Exception {
        String temporaryData = IOUtils.readByInputStreamReaderUTF("src/com/templet/ParamTemporaryFile");
        String table = EscapeCharEnum.getEscapes("TABLE");

        String[] temporarys = temporaryData.split(EscapeCharEnum.getEscapes("NEWLINE"));
        IData columnData = new DataMap();
        IData columnDataSub = new DataMap();

        if (ValidatorUtils.isNonBlank(temporarys)) {
            int temporarysLength = temporarys.length;

            int columns = temporarys[0].split(table).length;

            for (int i = 0; i < columns; i++) {
                columnData.put(i + "", "");
            }

            //遍历每一行
            for (int i = 0; i < temporarysLength; i++) {
                String temporary = temporarys[i];
                if (ValidatorUtils.isNonBlank(temporary)) {
                    String[] temporaryColumns = temporary.split(table);
                    if (ValidatorUtils.isNonBlank(temporaryColumns)) {
                        //遍历每一列
                        for (int i1 = 0; i1 < temporaryColumns.length; i1++) {
                            String temporaryColumn = temporaryColumns[i1];
                            if (ValidatorUtils.isBlank(temporaryColumn)) {
                                continue;
                            }
                            switch (i1) {
                                case 0:
                                    //temporaryColumn = "1" + temporaryColumns[i1];
                                    //temporaryColumn = temporaryColumns[i1] + "(成员产品)";
                                    break;
                                case 1:
                                    //temporaryColumn = temporaryColumns[i1] + "(成员产品)";
                                case 2:

                                    break;
                                case 3:

                                    break;
                                case 4:

                                    break;
                                default:
                                    break;
                            }

                            String columnDataStr = columnData.getString(i1 + "", "");
                            //if (!columnDataStr.contains(temporaryColumn)) {
                            columnDataStr = characterSplice(columnDataStr, temporarysLength, i, temporaryColumn);
                            columnData.put(i1 + "", columnDataStr);
                            //}
                            if (temporaryColumn.length() == 8 && RegularUtils.numberCheck(temporaryColumn)) {
                                String temporaryColumnSub = temporaryColumn.substring(2);
                                String temporaryColumnSubs = columnDataSub.getString(i1 + "", "");
                                //if (!temporaryColumnSubs.contains(temporaryColumnSub)) {
                                temporaryColumnSubs = characterSplice(temporaryColumnSubs, temporarysLength, i, temporaryColumnSub);
                                columnDataSub.put(i1 + "", temporaryColumnSubs);
                                //}
                            }
                        }
                    }
                }
            }
        }
        if (ValidatorUtils.isNonNull(columnData)) {
            for (int i = 0; i < columnData.size(); i++) {
                PrintUtils.printlns(columnData.getString(i + ""));
                PrintUtils.printlns(columnDataSub.getString(i + ""));
            }
        }
    }

    private static String characterSplice(String discntCodes, int length, int i, String string) {
        //if (RegularUtils.numberCheck(string)) {
        //    string = remove(string, ' ');
        //    if (i == 0) {
        //        discntCodes += "(" + string + ",";
        //    } else if (i == (length - 1)) {
        //        discntCodes += "" + string + ")";
        //    } else {
        //        discntCodes += "" + string + ",";
        //    }
        //}else {
        string = remove(string, ' ');
        if (i == 0) {
            discntCodes += "\'" + string + "\',";
        } else if (i == (length - 1)) {
            discntCodes += "\'" + string + "\'";
        } else {
            discntCodes += "\'" + string + "\',";
        }
        //}
        return discntCodes;
    }

    /**
     * description: <b>删除字符串中指定字符</b>
     *
     * @param resource
     * @param ch
     * @return java.lang.String
     * @author xiewy
     * @date 2020/7/28 11:05 下午
     */
    public static String remove(String resource, char ch) {
        StringBuffer buffer = new StringBuffer();
        int position = 0;
        char currentChar;
        try {
            while (true) {
                currentChar = resource.charAt(position++);
                if (currentChar != ch) {
                    buffer.append(currentChar);
                }
            }
        } catch (Exception e) {
        }
        return buffer.toString();
    }


}
