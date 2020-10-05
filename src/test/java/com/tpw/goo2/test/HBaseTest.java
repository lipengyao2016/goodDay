package com.tpw.goo2.test;

import com.tpw.goo.test.HBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HBaseTest extends BaseUnitTest {

    @Resource
    HBaseUtils hBaseUtils;
    String tableName = "Student";



    @Test
    public void createTable() throws Exception {
        hBaseUtils.createTable(tableName,new String[]{"baseInfo","schoolInfo"});
    }

    @Test
    public void writeRow() throws IOException {
        String rowkey = "goods";
        String[] baseInfoCols = {"data_id","title","shop_name"};
        String[] schoolInfoCols = {"price","coupon_price","pingou_price"};
        long lCur = System.currentTimeMillis();
        Map<String,Map<String,Object>> rowDataList = new HashMap<String,Map<String,Object>>();
        for (int i = 5000; i < 6000; i++) {
            String curRowKey  =rowkey + i;
            Map<String,Object> curRowDataMap = new HashMap<String, Object>();
            for (String baseCol:baseInfoCols) {
                curRowDataMap.put("baseInfo:" + baseCol,"base_" + baseCol + i);
            }
            for (String schCol:schoolInfoCols) {
                curRowDataMap.put("schoolInfo:" + schCol,"sch_"+schCol + i);
            }
            rowDataList.put(curRowKey,curRowDataMap);
        }
        hBaseUtils.batchWrite(tableName,rowDataList);
        System.out.println(" write end time:" + (System.currentTimeMillis() - lCur));
    }

    @Test
    public void updateOneRow() throws IOException {
        String rowkey = "goods";
        String[] baseInfoCols = {"data_id","title","shop_name"};
        String[] schoolInfoCols = {"price","coupon_price","pingou_price"};
        long lCur = System.currentTimeMillis();

        String curRowKey  ="goods9";
        Map<String,Object> curRowDataMap = new HashMap<String, Object>();
        for (String baseCol:baseInfoCols) {
            curRowDataMap.put("baseInfo:" + baseCol,baseCol + "sz");
        }

        for (String schCol:schoolInfoCols) {
            curRowDataMap.put("schoolInfo:" + schCol,schCol + "sz");
        }
        hBaseUtils.write(tableName,curRowKey,curRowDataMap);

        System.out.println(" write end time:" + (System.currentTimeMillis() - lCur));
    }


    @Test
    public void readRow(){
        Result res = hBaseUtils.read(tableName,"goods9");
        System.out.println(" read row1 res:" + Bytes.toString(res.getRow()));
        hBaseUtils.printCell(res);
    }

    @Test
    public void listRow() throws IOException {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
//            SingleColumnValueFilter nameFilter = new SingleColumnValueFilter(Bytes.toBytes(tableName),
//                    Bytes.toBytes("baseInfo"), CompareFilter.CompareOp.EQUAL,  new SubstringComparator("3999"));
//            nameFilter.setFilterIfMissing(true);
        //    filterList.addFilter(nameFilter);

//            Filter rowfilter  = new RowFilter(CompareFilter.CompareOp.EQUAL,
////                    new BinaryComparator(Bytes.toBytes("row10"))
////                    new BinaryPrefixComparator(Bytes.toBytes("row109"))
//                    new SubstringComparator("row1099")
//            );
//            filterList.addFilter(rowfilter);

//                        Filter rowfilter  = new ValueFilter(CompareFilter.CompareOp.EQUAL,
////                    new BinaryComparator(Bytes.toBytes("row10"))
////                    new BinaryPrefixComparator(Bytes.toBytes("row109"))
//                    new SubstringComparator("age")
//            );
//        DependentColumnFilter rowfilter = new DependentColumnFilter(
//                Bytes.toBytes("baseInfo"),
//                Bytes.toBytes("data_id"),
//                true,
//                CompareFilter.CompareOp.EQUAL,
//                new SubstringComparator("base_data_id"));

        PrefixFilter rowfilter = new PrefixFilter(Bytes.toBytes("goods49"));
        filterList.addFilter(rowfilter);

        FirstKeyOnlyFilter firstKeyOnlyFilter = new FirstKeyOnlyFilter();
        filterList.addFilter(firstKeyOnlyFilter);

        String startRowKey = "";
        int totalCnt = 0;
        while (true)
        {
            ResultScanner scanner = hBaseUtils.getScannerWithPage(tableName, startRowKey, 3, filterList);
            int cnt = 0;
            if (scanner != null) {
                while (true) {
                    Result res2 = scanner.next();
                    if (res2 == null) {
                        break;
                    }
                    startRowKey = hBaseUtils.printCell(res2);
                    cnt++;
                }
                System.out.println(" search cnt:" + cnt + " totalCnt:" + totalCnt);
                scanner.close();
                totalCnt += cnt;
            }
            if (cnt <= 0)
            {
                System.out.println(" search end" );
                break;
            }
        }
        System.out.println(" search totalCnt:" + totalCnt);


    }


    @Test
    public void countRows(){
         hBaseUtils.count("UserMingXi");
    }


}
