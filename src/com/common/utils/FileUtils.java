package com.common.utils;

import com.ai.acctcomp.base.util.StringUtil;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataset;
import com.ailk.common.data.impl.DataMap;
import com.ailk.common.data.impl.DatasetList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.*;

import static com.common.utils.RandomStrUtils.getRandomFileNameAscend;

/**
 * description: 文件工具类
 *
 * @author: xiewy
 * @date: Created in 2020/9/30 9:09 下午
 * @version: V1.0
 */
public class FileUtils {

    /**
     * 筛选器 : 对文件大小排序
     */
    private static final int filterFileSizeSort = 3;
    private static final IData allFilePaths = new DataMap();
    private static int allFileCount = 0;

    /**
     * description: <b>folderPath文件夹下的所有文件(包括多级目录下的文件)移动到新文件夹中</b>
     * <p>新文件夹的父路径 newFolderPath</p>
     * <p>新文件夹的名称规律 : folderName + 数字</p>
     * <p>每个新文件夹放 fileNameCount 个文件</p>
     *
     * @param folderPath    把该文件夹下的所有文件移动
     * @param newFolderPath 新文件夹的父路径
     * @param folderName    新文件夹的名称规律:folderName + 数字
     * @param fileNameCount 每个新文件夹放置文件数
     * @return void
     * @author xiewy
     * @date 2020/1/31 6:37 下午
     */
    public static void moveToSubfolder(String folderPath, String newFolderPath, String folderName, int fileNameCount) {
        File[] allfileLists = getAllSortFilePathByFileNameAsc(folderPath);

        int folderCount = 0;
        IDataset filesList = new DatasetList();
        for (File file : allfileLists) {
            if (file.isFile() && !file.isHidden()) {
                IData filesMap = new DataMap();
                filesMap.put("FILE_PATH", file.getPath());
                filesList.add(filesMap);
                if (filesList.size() == fileNameCount) {
                    folderCount++;
                    moveFiles(newFolderPath, folderName, filesList, folderCount);
                    filesList.clear();
                }
            }
        }
        if (filesList != null && filesList.size() > 0) {
            folderCount++;
            moveFiles(newFolderPath, folderName, filesList, folderCount);
        }
    }

    public static File[] getFiles(String folderPaths) {
        File folderPath = new File(folderPaths);
        return folderPath.listFiles();
    }

    public static void moveFiles(String newFolderPath, String fileName, IDataset filesList, int fileNameCount) {
        createFoldersBatches(newFolderPath, fileName, fileNameCount, 1);
        String folder = "";
        if (fileNameCount < 10) {
            folder = newFolderPath + "/" + fileName + "00" + fileNameCount;
        }else if (fileNameCount >= 10 && fileNameCount < 100) {
            folder = newFolderPath + "/" + fileName + "0" + fileNameCount;
        } else {
            folder = newFolderPath + "/" + fileName + fileNameCount;
        }
        for (int i = 0; i < filesList.size(); i++) {
            String filePath = filesList.getData(i).getString("FILE_PATH");
            File file = new File(filePath);
            file.renameTo(new File(folder + "/" + file.getName()));
        }
    }

    /**
     * description: <b>批量创建文件夹</b>
     * <p>名称规律 : xxx01,xxx02,xxx03......xxx22......</p>
     *
     * @param folderPath    在该文件夹下批量创建新子文件夹
     * @param fileName      新文件夹的名称
     * @param fileNameCount 新文件夹名称后面的数字
     * @param fileNumber    创建文件夹个数
     * @return void
     * @author xiewy
     * @date 2020/1/31 4:15 下午
     */
    public static void createFoldersBatches(String folderPath, String fileName, int fileNameCount, int fileNumber) {
        for (int i = fileNameCount; i < (fileNumber + fileNameCount); i++) {
            String pathName = "";
            if (i < 10) {
                pathName = folderPath + "/" + fileName + "00" + i;
            }else if (fileNameCount >= 10 && fileNameCount < 100) {
                pathName = folderPath + "/" + fileName + "0" + i;
            } else {
                pathName = folderPath + "/" + fileName + i;
            }
            File file = new File(pathName);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    /**
     * <p>批量修改文件夹下的所有文件的文件名 (不包括隐藏文件和多级目录下的文件)</p>
     * <p>新文件名称 : newName_4位数字(newName_0001、newName_00002......)</p>
     * <p>默认按照文件大小从小到大</p>
     *
     * @param folderPath 文件夹路径
     * @param newName    新名称
     */
    public static void updateFileNamesBatch(String folderPath, String newName) {
        updateFileNamesBatch(folderPath, newName, true, filterFileSizeSort, 0);
    }

    /**
     * description: <b>批量修改文件夹下的所有文件的文件名 (不包括隐藏文件和多级目录下的文件)</b>
     * <p>新文件名称 : 随机 ，升序</p>
     * <p>默认按照文件大小从小到大</p>
     *
     * @param folderPath
     * @return void
     * @author xiewy
     * @date 2020/10/1 9:07 上午
     */
    public static void updateFileNamesBatch(String folderPath) throws Exception {
        //获取其file对象
        File file = new File(folderPath);
        if (file.isFile()) { //传入的路径是文件路径
            System.out.println("不是文件夹路径: " + folderPath);
        } else {
            File[] fs = listFiles(folderPath, true, filterFileSizeSort);
            int minNum = 0;
            for (File oldFile : fs) {
                //文件后缀名
                String fileSuffix = getFileSuffix(oldFile.getName());
                //新文件路径
                String parent = oldFile.getParent();
                //新文件路径 + 新文件名
                String newPath;
                String newName = getRandomFileNameAscend(minNum);
                newPath = parent + "/" + newName + "." + fileSuffix;
                updateFileName(oldFile, new File(newPath));
                minNum++;
            }
            //修改完成后打开文件夹
            //openFolderOrFile(folderPath);
        }
    }

    /**
     * <p>批量修改文件夹下的所有文件的文件名 (不包括隐藏文件和多级目录下的文件)</p>
     * <p>新文件名称 : newName_4位数字(newName_0001、newName_00002......)</p>
     *
     * @param folderPath 旧文件夹路径
     * @param newName    新文件名称
     * @param order      true: 升序 ; false:降序
     * @param filter     文件排序规则
     */
    public static int updateFileNamesBatch(String folderPath, String newName, Boolean order, int filter, int count) {
        //获取其file对象
        File file = new File(folderPath);
        if (file.isFile()) { //传入的路径是文件路径
            System.out.println("不是文件夹路径: " + folderPath);
        } else {
            File[] fs = listFiles(folderPath, order, filter);
            for (File f : fs) {
                String counts = "";
                if (count <= 9) {
                    counts = "0000" + count;
                } else if (count > 9 && count <= 99) {
                    counts = "000" + count;
                } else if (count > 99 && count <= 999) {
                    counts = "00" + count;
                } else if (count > 999 && count <= 9999) {
                    counts = "0" + count;
                } else if (count > 9999 && count <= 99999) {
                    counts = "" + count;
                }
                //文件后缀名
                String fileSuffix = getFileSuffix(f.getName());
                //新文件路径
                String parent = f.getParent();
                //新文件路径 + 新文件名
                String newPath;
                if ("".equals(newName)) {
                    newPath = parent + "/" + counts + "." + fileSuffix;
                } else {
                    newPath = parent + "/" + newName + "_" + counts + "." + fileSuffix;
                }
                updateFileName(f, new File(newPath));
                count++;
            }
            //修改完成后打开文件夹
            //openFolderOrFile(folderPath);
        }
        return count;
    }

    /**
     * description: <b>修改文件或文件夹名称</b>
     *
     * @param oldName 原文件或文件夹
     * @param newName 修改后文件或文件夹
     * @return void
     * @author xiewy
     * @date 2020/1/20 5:28 下午
     */
    public static void updateFileName(File oldName, File newName) {
        oldName.renameTo(newName);
    }

    /**
     * description: <b>获取文件后缀名</b>
     *
     * @param name 文件名称.后缀名 (不包括文件路径)
     * @return java.lang.String 后缀名
     * @author xiewy
     * @date 2020/1/20 5:34 下午
     */
    public static String getFileSuffix(String name) {
        if (StringUtil.isBlank(name)) {
            return "";
        } else {
            return name.substring(name.lastIndexOf(".") + 1);
        }
    }

    /**
     * 获取指定文件夹下的所有文件(排序)
     * <p>过滤文件夹和隐藏文件</p>
     *
     * @param filePath 指定文件夹路径
     * @param sequence true:升序 ; false:降序
     * @param filter   筛选规则
     * @return
     */
    public static File[] listFiles(String filePath, boolean sequence, int filter) {
        File files = new File(filePath);
        List<File> fileList = new ArrayList<File>(Arrays.asList(files.listFiles()));
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (file.isHidden() || file.isDirectory()) {
                fileList.remove(file);
                i--;
                continue;
            }
        }
        File[] result = new File[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            result[i] = fileList.get(i);
        }
        Comparator<File> c = getFileComparator(filter, true);
        Arrays.sort(result, c);
        return result;
    }

    /**
     * description: <b> 获取文件比较器 </b>
     *
     * @param type  <p>1 : 修改时间排序</p>
     *              <p>2 : 按文件名排序(有文件夹)</p>
     *              <p>3 : 按文件大小排序</p>
     *              <p>4 : 对数字文件名排序</p>
     *              <p>5 : 按文件名排序(没有文件夹)</p>
     *              <p></p>
     * @param order <p>1 (true:从旧到新,false:从新到旧)</p>
     *              <p>2 (true:升序,false:降序)</p>
     *              <p>3 (true:从小到大,false:从大到小)</p>
     *              <p>4 (true:升序,false:降序)</p>
     *              <p>5 (true:升序,false:降序)</p>
     * @return java.util.Comparator<java.io.File>
     * @author xiewy
     * @date 2020/1/17 3:39 下午
     */
    public static Comparator<File> getFileComparator(int type, Boolean order) {
        switch (type) {
            case 1:
                return new Comparator<>() {
                    @Override
                    public int compare(File f1, File f2) {
                        long diff = f1.lastModified() - f2.lastModified();
                        if (order) { //按修改时间从旧到新
                            if (diff > 0) {
                                return 1;
                            } else if (diff == 0) {
                                return 0;
                            } else {
                                return -1;
                            }
                        } else { //按修改时间从新到旧
                            if (diff < 0) {
                                return 1;
                            } else if (diff == 0) {
                                return 0;
                            } else {
                                return -1;
                            }

                        }
                    }
                };
            case 2:
                return new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if (o1.isDirectory() && o2.isFile()) {
                            return -1;
                        }
                        if (o1.isFile() && o2.isDirectory()) {
                            return 1;
                        }
                        if (order) { //true: 按文件名升序
                            return o1.getName().compareTo(o2.getName());
                        } else { //false: 按文件名降序
                            return o2.getName().compareTo(o1.getName());
                        }
                    }
                };
            case 3:
                return new Comparator<File>() {
                    @Override
                    public int compare(File f1, File f2) {
                        long diff = f1.length() - f2.length();
                        if (order) { //文件从小到大
                            if (diff > 0) {
                                return 1;
                            } else if (diff == 0) {
                                return 0;
                            } else {
                                return -1;
                            }
                        } else { //文件从大到小
                            if (diff < 0) {
                                return 1;
                            } else if (diff == 0) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    }
                };
            case 4:
                return new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        //隐藏文件 .DS_Store
                        String fileName1 = getFileName(o1.getName());
                        String fileName2 = getFileName(o2.getName());
                        if ("".equals(fileName1)) {
                            fileName1 = "0";
                        }
                        if ("".equals(fileName2)) {
                            fileName2 = "0";
                        }
                        int o1Name = 0;
                        int o2Name = 0;
                        o1Name = Integer.parseInt(fileName1);
                        o2Name = Integer.parseInt(fileName2);
                        if (order) { //true: 对数字文件名升序
                            if (o1Name > o2Name) {
                                return 1;
                            } else if (o1Name == o2Name) {
                                return 0;
                            } else {
                                return -1;
                            }
                        } else { //false: 对数字文件名降序
                            if (o1Name < o2Name) {
                                return 1;
                            } else if (o1Name == o2Name) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    }
                };
            case 5:
                return new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if (order) { //true: 按文件名升序
                            return o1.getName().compareTo(o2.getName());
                        } else { //false: 按文件名降序
                            return o2.getName().compareTo(o1.getName());
                        }
                    }
                };
            default:
                return null;
        }

    }

    /**
     * description: <b>获取文件名(不包括后缀)</b>
     *
     * @param name 文件名称.后缀名
     * @return java.lang.String  文件名(不包括后缀)
     * @author xiewy
     * @date 2020/1/20 5:35 下午
     */
    public static String getFileName(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        } else {
            return name.substring(0, name.lastIndexOf("."));
        }
    }

    /* *
     * description: <b> 获取指定文件夹下的所有文件(包括多级目录下的文件,不包括隐藏文件)</b>
     * @param filePath
     * @return com.ailk.common.data.IData
     * @author xiewy
     * @date 2020/12/20 2:31 下午
     */
    public static IData getAllFilePath(String filePath) {
        File files = new File(filePath);
        File[] listFiles = files.listFiles();
        for (File listFile : listFiles) {
            if (!listFile.isHidden()) {
                if (listFile.isDirectory()) {
                    getAllFilePath(listFile.getPath());
                }

                if (listFile.isFile()) {
                    allFilePaths.put(allFileCount + "", listFile.getPath());
                    allFileCount++;
                }
            }
        }
        return allFilePaths;
    }

    public static IData getAllFilePath(File files) {
        File[] listFiles = files.listFiles();
        for (File listFile : listFiles) {
            if (!listFile.isHidden()) {
                if (listFile.isDirectory()) {
                    getAllFilePath(listFile.getPath());
                }

                if (listFile.isFile()) {
                    allFilePaths.put(allFileCount + "", listFile.getPath());
                    allFileCount++;
                }
            }
        }
        return allFilePaths;
    }

    /**
     * description: <b>批量修改文件夹下的所有文件的文件名 (包括多级目录下的文件,不包括隐藏文件)，无排序规则</b>
     *
     * @param folderPath
     * @return void
     * @author xiewy
     * @date 2020/10/11 5:16 下午
     */
    //private static void updateAllFileNamesBatch(String folderPath) throws Exception {
    //    //获取其file对象
    //    File file = new File(folderPath);
    //    if (file.isFile()) { //传入的路径是文件路径
    //        throw new Exception("Non folder path : " + folderPath);
    //    }
    //    //得到文件夹下的所有文件（排除隐藏文件）
    //    IData allFilePaths = getAllFilePath(file);
    //    Set<String> keySets = allFilePaths.keySet();
    //    int minNum = 1;
    //    for (String keySet : keySets) {
    //        String valueSet = allFilePaths.getString(keySet);
    //        File oldFile = new File(valueSet);
    //        //文件后缀名
    //        String fileSuffix = getFileSuffix(oldFile.getName());
    //        //新文件路径
    //        String parent = oldFile.getParent();
    //        //新文件路径 + 新文件名
    //        String newPath = "";
    //        String newName = getRandomFileNameAscend(minNum);
    //        newPath = parent + "/" + newName + "." + fileSuffix;
    //        updateFileName(oldFile, new File(newPath));
    //        minNum++;
    //    }
    //}

    /**
     * description: <b>oldFolderPath 文件夹下的所有文件(包括多级目录下的文件)汇总移动到新文件夹中</b>
     * <p>新文件夹的父路径 newFolderPath</p>
     * <p>新文件夹的名称规律 : folderName + 数字</p>
     * <p>汇总新文件夹放 10000000 个文件</p>
     *
     * @param oldFolderPath
     * @param newFolderPath
     * @param folderName
     * @return void
     * @author xiewy
     * @date 2020/10/27 6:04 下午
     */
    public static void moveAllFile(String oldFolderPath, String newFolderPath, String folderName) {
        File[] listFiles = getAllSortFilePathByFileNameAsc(oldFolderPath);
        for (File listFile : listFiles) {
            if (listFile.isDirectory() && !listFile.isHidden()) {
                moveAllFile(listFile.getPath(), newFolderPath, folderName);
            }
        }
        moveToSubfolder(oldFolderPath, newFolderPath, folderName, Integer.MAX_VALUE);
    }

    /**
     * description: <b>将文件名称改成上级文件夹名称 </b>
     * <p>将该文件夹下的所有文件的名称修改成 : 上级文件夹名称 + "_" + 文件名称</p>
     *
     * @param folderPath
     * @return void
     * @author xiewy
     * @date 2020/11/16 9:52 上午
     */
    public static void updateFileNamesToFoldeName(String folderPath) {
        ArrayList<String> allFileLists = getAllFileList(folderPath);
        for (String allFileList : allFileLists) {
            File file = new File(allFileList);
            String path = file.getPath();
            String[] strings = path.split("/");
            String folderName = strings[strings.length - 2];
            String fileName = file.getName();
            String newFileName = file.getParent() + "/" + folderName + "_" + fileName;
            updateFileName(file, new File(newFileName));
        }
    }

    /**
     * description: <b>批量修改文件夹下的所有文件的文件名 (包括多级目录下的文件,不包括隐藏文件)，按文件名升序的排序规则</b>
     * <p>文件名称规律 ： yyyy_MMdd_4位数字_4位随机字符_4位随机字符_4位随机字符</p>
     *
     * @param folderPath
     * @return void
     * @author xiewy
     * @date 2020/11/24 6:04 下午
     */
    public static void updateAllFileNamesBatch(String folderPath) throws Exception {
        File[] oldFiles = getAllSortFilePathByFileNameAsc(folderPath);
        int minNum = 1;
        for (File oldFile : oldFiles) {
            //文件后缀名
            String fileSuffix = getFileSuffix(oldFile.getName());
            //新文件路径
            String parent = oldFile.getParent();
            //新文件路径 + 新文件名
            String newPath = "";
            String newName = getRandomFileNameAscend(minNum);
            newPath = parent + "/" + newName + "." + fileSuffix;

            updateFileName(oldFile, new File(newPath));
            minNum++;
        }
    }

    /**
     * description: <b>获取文件夹下所有文件的绝对路径(包括多级目录,不包括隐藏文件)</b>
     *
     * @param folderPath
     * @return java.util.ArrayList<java.lang.String>
     * @author xiewy
     * @date 2020/11/25 4:44 下午
     */
    private static ArrayList<String> getAllFileList(String folderPath) {
        ArrayList<String> allfileLists = new ArrayList<>();
        allfileLists = getAllFiles(folderPath, allfileLists);
        return allfileLists;
    }

    /**
     * description: <b>获取文件夹下所有文件的绝对路径(包括多级目录,不包括隐藏文件)</b>
     *
     * @param folderPath
     * @param allfileLists
     * @return java.util.ArrayList<java.lang.String>
     * @author xiewy
     * @date 2020/11/25 4:47 下午
     */
    private static ArrayList<String> getAllFiles(String folderPath, ArrayList<String> allfileLists) {
        File files = new File(folderPath);
        File[] listFiles = files.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File listFile : listFiles) {
                if (!listFile.isHidden()) { //隐藏文件不处理
                    if (listFile.isFile()) {
                        allfileLists.add(listFile.getPath());
                    } else if (listFile.isDirectory()) {
                        getAllFiles(listFile.getPath(), allfileLists);
                    }
                }
            }
        }
        return allfileLists;
    }

    /**
     * description: <b>获取文件夹下的所有文件(最多 10 万个文件)</b>
     * <b><p>按文件名升序的排序规则</p></b>
     * <b><p>包括多级目录下的文件</p></b>
     * <b><p>不包括隐藏文件</p></b>
     *
     * @param folderPath <b>指定文件夹</b>
     * @return java.io.File[]
     * @author xiewy
     * @date 2020/12/20 3:05 下午
     */
    public static File[] getAllSortFilePathByFileNameAsc(String folderPath) {
        return getAllFilePathBySortRules(folderPath, 5, true);
    }

    /**
     * description: <b>获取指定文件夹下的所有文件(最多 10 万个文件)</b>
     * <b><p>按指定排序规则</p></b>
     * <b><p>包括多级目录下的文件</p></b>
     * <b><p>不包括隐藏文件</p></b>
     *
     * @param folderPath 指定文件夹
     *                   <p></p>
     * @param filtertype <p>1 : 修改时间排序</p>
     *                   <p>2 : 按文件名排序(有文件夹)</p>
     *                   <p>3 : 按文件大小排序</p>
     *                   <p>4 : 对数字文件名排序</p>
     *                   <p>5 : 按文件名排序(没有文件夹)</p>
     *                   <p> </p>
     * @param order      <p>1 (true:从旧到新,false:从新到旧)</p>
     *                   <p>2 (true:升序,false:降序)</p>
     *                   <p>3 (true:从小到大,false:从大到小)</p>
     *                   <p>4 (true:升序,false:降序)</p>
     *                   <p>5 (true:升序,false:降序)</p>
     * @return java.io.File[]
     * @author xiewy
     * @date 2020/12/20 2:56 下午
     */
    public static File[] getAllFilePathBySortRules(String folderPath, int filtertype, boolean order) {
        File[] listFiles = new File[100000];
        int count = 0;
        IData files = getAllFilePath(folderPath);
        Set<String> keySets = files.keySet();
        for (String keySet : keySets) {
            String filePath = files.getString(keySet);
            listFiles[count] = new File(filePath);
            count++;
        }

        List<File> fileList = new ArrayList<File>(Arrays.asList(listFiles));
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (file == null || file.length() == 0 || file.isHidden() || file.isDirectory()) {
                fileList.remove(file);
                i--;
                continue;
            }
        }

        File[] sortFiles = new File[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            sortFiles[i] = fileList.get(i);
        }

        Arrays.sort(sortFiles, getFileComparator(filtertype, order));
        return sortFiles;
    }

    /**
     * description: <b>获取视频大小</b>
     *
     * @param source
     * @param unitType B,KB,MB,GB
     * @return java.lang.String
     * @author xiewy
     * @date 2020/12/20 7:26 下午
     */
    public static String readVideoSize(File source, String unitType) {
        FileChannel fc = null;
        String size = "";
        try {
            @SuppressWarnings("resource")
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            switch (unitType) {
                case "B":
                    size = fileSize.toString();
                    break;
                case "KB":
                    size = fileSize.divide(new BigDecimal(1024), 1, RoundingMode.DOWN).toString();
                    break;
                case "MB":
                    size = fileSize.divide(new BigDecimal(1024 * 1024), 1, RoundingMode.DOWN).toString();
                    break;
                case "GB":
                    size = fileSize.divide(new BigDecimal(1024 * 1024 * 1024), 1, RoundingMode.DOWN).toString();
                    break;
                default:
                    size = fileSize.toString();
                    break;
            }

            //size = fileSize.divide(new BigDecimal(1048576), 1, RoundingMode.DOWN) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /**
     * description: <b>指定文件夹(oldFolderPath)下的所有文件移动到新文件夹(newFolderPath)中</b>
     * <p>包括多级目录下的文件</p>
     * <p>不包括隐藏文件</p>
     * <p>按新文件夹下所有文件大小之和</p>
     *
     * @param oldFolderPath
     * @param newFolderPath
     * @param sizeMax       每个文件夹下所有文件大小之和的上限,单位: KB
     * @param folderName    新文件夹名称
     * @return void
     * @author xiewy
     * @date 2020/12/21 1:29 上午
     */
    public static void moveToSubfolderByFolderSize(String oldFolderPath, String newFolderPath, double sizeMax, String folderName) {
        File[] files = getAllSortFilePathByFileNameAsc(oldFolderPath);
        double countSize = 0;//每个文件夹下所有文件的之和
        IDataset filesList = new DatasetList();
        int folderCount = 0;
        for (File file : files) {
            IData filesMap = new DataMap();
            filesMap.put("FILE_PATH", file.getPath());
            filesList.add(filesMap);
            countSize += Double.parseDouble(readVideoSize(file, "KB"));
            if (countSize >= sizeMax) {
                folderCount++;
                moveFiles(newFolderPath, folderName, filesList, folderCount);
                countSize = 0;
                filesList.clear();
            }
        }
        if (filesList != null && filesList.size() > 0) {
            folderCount++;
            moveFiles(newFolderPath, folderName, filesList, folderCount);
        }
    }
}
