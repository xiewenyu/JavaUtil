package com.common;

import com.ailk.common.data.IData;
import com.ailk.common.data.IDataset;
import com.ailk.common.data.impl.DataMap;
import com.common.enums.EscapeCharEnum;
import com.common.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.common.utils.FileUtils.*;
import static com.common.utils.IOUtils.close;
import static com.common.utils.IOUtils.readFileByInputStream;
import static com.common.utils.JsonUtils.jsonToIData;
import static com.common.utils.JsonUtils.jsonToIDataSet;

/**
 * description:
 *
 * @author: xiewy
 * @date: Created in 2021/3/7 4:58 下午
 * @version:
 */
public class TestCommonUtils {

    private static String oldFolderPath = "/Volumes/LaCie（2TB）/照片";
    private static String newFolderPath = "/Volumes/LaCie（2TB）/照片/KUNI Scan Complete Collection";
    private static final String[] numArr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] lowerCaseArr = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
    private static final String[] upperCaseArr = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
    public static void otherMethods() throws Exception {
        //批量修改文件夹下的所有文件的文件名 (包括多级目录下的文件,不包括隐藏文件)，按文件名升序的排序规则
        //FileUtils.updateAllFileNamesBatch(oldFolderPath);

        //指定文件夹(oldFolderPath)下的所有文件(包括多级目录下的文件)移动到新文件夹(newFolderPath)中
        //FileUtils.moveToSubfolder(oldFolderPath, newFolderPath, "韩国女团热舞", 100);

        //按新文件夹下所有文件大小之和将指定文件夹(oldFolderPath)下的所有文件移动到新文件夹(newFolderPath)中
        //FileUtils.moveToSubfolderByFolderSize(oldFolderPath, newFolderPath, 1024 * 1024 * 30, "未分类汇总");

        //指定文件夹(oldFolderPath)下的所有文件(包括多级目录下的文件)汇总移动到新文件夹(newFolderPath)中
        //FileUtils.moveAllFile(oldFolderPath, newFolderPath, "汇总");

        //将文件名称改成上级文件夹名称
        //FileUtils.updateFileNamesToFoldeName("/Users/xiewy/Desktop/未命名文件夹/汇总1");

        //哔哩哔哩收藏夹批量获取下载链接
        //把文件夹下（/Users/xiewy/Movies/HotDanceBiliBili-List）的B站收藏视频地址list导入到文件（/Users/xiewy/Movies/HotDanceBilibili.txt）中
        //blibliFavoritesDowm();

        //按文件大小分类
        //sortByFileSize();

        //创建文件夹(在 oldFolderPath 文件夹下创建 26 个英文字母文件夹)
        //mkdirFolder();

        //移动文件按首字母 移动到对应的字母文件夹下
        moveFolder();
    }

    private static void moveFolder() {
        File files = new File(newFolderPath);
        File[] listFiles = files.listFiles();
        for (File listFile : listFiles) {
            if (listFile.isDirectory()) {
                String listFileName = listFile.getName();
                String substring = listFileName.substring(0, 1);
                listFile.renameTo(new File(oldFolderPath + "/" + substring + "/ "+ listFile.getName()));
            }
        }
    }

    private static void mkdirFolder() {
        for (String s : upperCaseArr) {
            File file = new File("/Volumes/LaCie（2TB）/照片/" + s);
            file.mkdirs();
        }
    }

    /* *
     * description: <b>哔哩哔哩收藏夹批量获取下载链接 : 每次下载一页</b>
     * @param
     * @return void
     * @author xiewy
     * @date 2020/12/13 11:58 上午
     */
    private static void blibliFavoritesDowm() throws IOException {

        //String temporaryData = IOUtils.readByInputStreamReaderUTF("src/com/templet/ParamTemporaryFile");
        File writeFile = new File("/Users/xiewy/Movies/HotDanceBilibili.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(writeFile, true);

        File[] files = getAllSortFilePathByFileNameAsc("/Users/xiewy/Movies/HotDanceBiliBili-List");
        for (File file : files) {
            String temporaryData = readFileByInputStream(file);
            IData iDataMaps = jsonToIData(temporaryData);
            String data = iDataMaps.getString("data");
            IData data2 = new DataMap(data);
            String medias = data2.getString("medias");
            IDataset iDatasets = jsonToIDataSet(medias);

            //fileOutputStream.write("\r".getBytes());
            for (int i = 0; i < iDatasets.size(); i++) {
                IData iData = iDatasets.getData(i);
                String title = iData.getString("title");
                String bvid = iData.getString("bvid");
                fileOutputStream.write(("https://www.bilibili.com/video/" + bvid).getBytes("UTF-8"));
                fileOutputStream.write(EscapeCharEnum.MAC_NEWLINE.toString().getBytes());
            }
        }
        close(fileOutputStream);
    }

    /**
     * description: <b>按文件大小分类</b>
     *
     * @param
     * @return void
     * @author xiewy
     * @date 2020/12/20 3:08 下午
     */
    private static void sortByFileSize() {
        IData allFilePaths = getAllFilePath("/Volumes/LaCie/影片/Hot Dance/汇总");
        Map<String, ArrayList<String>> fileSizes = new HashMap<>();//key : 视频的大小  value: 大小相同的文件的绝对路径
        for (int i = 0; i < allFilePaths.size(); i++) {
            String filePath = allFilePaths.getString(i + "");
            String videoSize = readVideoSize(new File(filePath), "MB") + "MB";

            ArrayList<String> lists = fileSizes.get(videoSize);

            if (lists != null && lists.size() > 0) {
                lists.add(filePath);
                fileSizes.put(videoSize, lists);
            } else {
                ArrayList<String> listsTemp = new ArrayList<>();
                listsTemp.add(filePath);
                fileSizes.put(videoSize, listsTemp);
            }
        }
        //根据每个文件大小 , 创建文件夹 , 文件夹名称是文件大小
        //然后把相同大小的文件移动到文件夹下
        String folderPath = "/Volumes/LaCie/影片/Hot Dance/汇总";
        Set<String> keySets = fileSizes.keySet();
        for (String keySet : keySets) {
            File file = new File(folderPath + "/" + keySet);
            file.mkdirs();
            ArrayList<String> lists = fileSizes.get(keySet);
            for (String list : lists) {
                File file1 = new File(list);
                file1.renameTo(new File(folderPath + "/" + keySet + "/" + file1.getName()));
            }
        }
    }
}
