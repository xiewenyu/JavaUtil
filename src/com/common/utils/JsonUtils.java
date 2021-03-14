package com.common.utils;

import com.ailk.common.data.IData;
import com.ailk.common.data.IDataset;
import com.ailk.common.data.impl.DataMap;
import com.ailk.common.data.impl.DatasetList;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description:
 *
 * @author: xiewy
 * @date: Created in 2020/12/13 5:54 上午
 * @version:
 */
public class JsonUtils {
    /**
     * Describtion: <b>  json 转 IDataset
     * <div>入参 :  [json]
     *
     * @return com.ailk.common.data.IDataset
     * @throws Exception
     * @Date 2020/3/13 3:52 PM
     * @author xiewy
     */
    public static IDataset jsonToIDataSet(String json) {
        List<Object> jsonArray = JSON.parseArray(json);
        IDataset reDataSet = new DatasetList();
        for (Object object : jsonArray) {
            //取出list里面的值转为IData
            IData reDataMap = new DataMap();
            Map<String, String> map = new HashMap<>();
            map = (Map<String, String>) object;
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                reDataMap.put(key, map.get(key));
            }
            reDataSet.add(reDataMap);
        }
        return reDataSet;
    }

    /* *
     * description: <b>json 转 IData</b>
     * @param json
     * @return com.ailk.common.data.IData
     * @author xiewy
     * @date 2020/12/13 6:10 上午
     */
    public static IData jsonToIData(String json) {
        Map<String, String> maps = (Map) JSON.parse(json);
        IData reDataSet = new DataMap();
        Set<String> keySets = maps.keySet();
        for (String keySet : keySets) {
            reDataSet.put(keySet, maps.get(keySet));
        }
        return reDataSet;
    }
}
