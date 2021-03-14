import com.ailk.acctcomp.util.DataHelper;
import com.ailk.acctcomp.util.TimeUtil;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataset;
import com.ailk.common.data.impl.DataMap;
import com.common.TestCommonUtils;
import com.common.enums.EscapeCharEnum;
import com.common.utils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static com.common.utils.IOUtils.close;
import static com.common.utils.IOUtils.readFileByInputStream;
import static com.common.utils.JsonUtils.jsonToIData;
import static com.common.utils.JsonUtils.jsonToIDataSet;
import static com.common.utils.RandomStrUtils.getRandomFileNameAscend;

/**
 * description: 测试类
 *
 * @author: xiewy
 * @date: Created in 2020/9/18 4:47 下午
 * @version:
 */
public class TestCls {

    public static void main(String[] args) throws Exception {
        //TestCommonUtils.otherMethods();
        ParamUtils.accountParamConf();
    }
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static void chechDepartId(Map<String, IData> displayMap) throws Exception {
        //是否展示数字货币类型,true显示，false不显示
        boolean szhbDisplayFlag = true;

        IData szhbCheck = new DataMap();//ParamMgr.getCommPara("ASM_SZHB_CHECK");
        szhbCheck.put("PARA_CODE1", "true");

        if (szhbCheck != null && szhbCheck.size() > 0) {
            if (szhbCheck.getBoolean("PARA_CODE1",false)) {
                IData szhbDepartid = new DataMap();//ParamMgr.getCommPara("ASM_SZHB_DEPARTID");
                szhbDepartid.put("PARA_CODE1", "");
                szhbDepartid.put("PARA_CODE2", "");

                if (szhbDepartid != null && szhbDepartid.size() > 0) {
                    String departId = "23067";//员工工号
                    String disPlayIdStr = "";
                    disPlayIdStr = szhbDepartid.getString("PARA_CODE1");
                    String paraCode2 = szhbDepartid.getString("PARA_CODE2");
                    if (ValidatorUtils.isNonBlank(paraCode2)) {
                        disPlayIdStr = disPlayIdStr + "," + paraCode2;
                    }
                    if (!disPlayIdStr.contains(departId)) {
                        szhbDisplayFlag = false;
                    }
                }else {

                }
            }
        }
        if (!szhbDisplayFlag) {
            //不显示
            displayMap.remove("SZHB");
            displayMap.remove("SZHB");
        }
    }
    private static String formatKb(long kb) {
        long mb = kb / 1024;
        if (mb >= 1024) {
            long gb = kb / (1024 * 1024);
            long leftKb = kb % (1024 * 1024);
            if (leftKb == 0) {
                return gb + "G";
            } else {
                String mbStr = DataHelper.format2Centi(leftKb / 1024.0);
                if (mbStr.substring(mbStr.indexOf(".") + 1).equals("00")) {
                    mbStr = mbStr.substring(0, mbStr.indexOf("."));
                }
                return gb + "G " + mbStr + "M";
            }
        } else {
            String mbStr = DataHelper.format2Centi(kb / 1024.0);
            if (mbStr.substring(mbStr.indexOf(".") + 1).equals("00")) {
                mbStr = mbStr.substring(0, mbStr.indexOf("."));
            }
            return mbStr + "M";
        }
    }

    /**
     * 移除多余空行和空格
     */
    public static String dealRedundantSpaceAndBlankLine(String content) {
        if (content == null || content.length() == 0) {
            return "";
        }
        StringBuilder strAfterRemoveCRSB = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) != '\r') {
                strAfterRemoveCRSB.append(content.charAt(i));
            }
        }
        String strAfterRemoveCR = strAfterRemoveCRSB.toString();
        if (strAfterRemoveCR == null || strAfterRemoveCR.length() == 0) {
            return "";
        }
        StringBuilder resultSB = new StringBuilder();
        String[] lines = strAfterRemoveCR.split("\n");
        int blankCount = 0;
        for (String line : lines) {
            if (line == null) {
                continue;
            }
            String lineTrim = line.trim();
            if ("".equals(lineTrim)) {
                blankCount++;
                if (blankCount <= 2) {
                    resultSB.append("\n");
                }
            } else {
                blankCount = 0;
                resultSB.append(dealSpace4OneLine(line)).append("\n");
            }
        }
        resultSB.deleteCharAt(resultSB.length() - 1);
        return resultSB.toString();
    }

    /**
     * 移除1行中的多余空格
     */
    public static String dealSpace4OneLine(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        int spaceCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char curChar = line.charAt(i);
            if (curChar == ' ') {
                spaceCount++;
                if (spaceCount <= 5) {
                    sb.append(' ');
                }
            } else {
                spaceCount = 0;
                sb.append(curChar);
            }
        }
        return sb.toString();
    }
}