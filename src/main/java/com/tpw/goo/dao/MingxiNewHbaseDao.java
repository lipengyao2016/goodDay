package com.tpw.goo.dao;


import com.tpw.goo.bean.UserMingxi;
import com.tpw.goo.test.HBaseUtils;
import com.tpw.goo.util.ReflectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MingxiNewHbaseDao {

    public String  table = "UserMingXi4";
    public Map<String,String[]> colFamily = new HashMap<String,String[]>();

    @Resource
    HBaseUtils hBaseUtils;





    public MingxiNewHbaseDao() {
        colFamily.put("userInfo",new String[]{"uid","team_uid","trade_uid"});
        colFamily.put("orderInfo",new String[]{"trade_id_former","create_time","shijian","money"});
    }

    public boolean initTable()
    {
        Object[]  keys = colFamily.keySet().toArray();
        String[]  keyTitles = new String[colFamily.size()];
        int i = 0;
        for (Object key:keys)
        {
            keyTitles[i++] = key.toString();
        }


        try {
            hBaseUtils.createTable(table,keyTitles);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        return  true;
    }

    public boolean WriteData(List<UserMingxi> userMingxis)
    {

        try {
            Map<String,Map<String,Object>> rowDataList = new HashMap<String,Map<String,Object>>();
            for (UserMingxi userMingxi : userMingxis) {
                String curRowKey  = userMingxi.getUid() + "" + userMingxi.getRelate_id() +userMingxi.getShijian() + userMingxi.getCreate_time();
                Map<String,Object> curRowDataMap = new HashMap<String, Object>();

                Map<String,Object> beanMap = ReflectUtils.getFieldMap(userMingxi);

                for (Map.Entry<String,String[]> entry:colFamily.entrySet()) {
                    String colFam = entry.getKey();
                    String[] cols = entry.getValue();
                    for (String baseCol:cols) {
                        curRowDataMap.put(colFam + ":" + baseCol,beanMap.get(baseCol));
                    }
                }

                if (rowDataList.containsKey(curRowKey))
                {
                    System.out.println(" cur row key is exist curRowKey:"  + curRowKey);
                    curRowKey += "_1";
                    rowDataList.put(curRowKey,curRowDataMap);
                }
                else{
                    rowDataList.put(curRowKey,curRowDataMap);
                }


            }

            System.out.println(" rowDataList size:" + rowDataList.size());
            hBaseUtils.batchWrite(table,rowDataList);
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return  false;
        }
        return  true;

    }
}
