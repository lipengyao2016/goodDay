package com.tpw.goo.test;

import org.apache.directory.api.util.Strings;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.rocksdb.ColumnFamilyDescriptor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HBaseUtils {

    public  void createTable(String tableName,String[] familys) throws Exception{
        System.out.println("begin");
        //创建表
        Admin admin = HBaseConfig.getConn().getAdmin();
        System.out.println("get admin ok");
        if (admin.tableExists(TableName.valueOf(tableName))){
            System.out.println("表已存在");
            return;
//            admin.disableTable(TableName.valueOf(tableName));
//            admin.deleteTable(TableName.valueOf(tableName));
        }
        System.out.println("check  table exist ok");

        HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
        for (String family:
                familys) {
            tableDesc.addFamily(new HColumnDescriptor(family));
        }

        admin.createTable(tableDesc);
        System.out.println(tableName + "表创建成功！");
    }

    public TableName getTableName(String tableName){
        return TableName.valueOf(tableName);
    }

    public Table getTable(TableName tableName){
        try {
            return HBaseConfig.getConn().getTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void batchWrite(String tableName, Map<String,Map<String, Object>> dataMap) throws IOException {
        Table table = this.getTable(getTableName(tableName));
        List<Put> putList= new ArrayList<Put>();

        for (Map.Entry<String,Map<String, Object>> singleData : dataMap.entrySet())
        {
            Put put = generatePut(singleData.getKey(), singleData.getValue());
            putList.add(put);
        }

        table.put(putList);
        table.close();
    }


    public void write(String tableName, String rowkey, Map<String, Object> keyValues) throws IOException {
        Table table = this.getTable(getTableName(tableName));
        Put put = generatePut(rowkey, keyValues);
        table.put(put);
    }

    public Put generatePut(String rowkey, Map<String, Object> keyValues){
        Put put = new Put(Bytes.toBytes(rowkey));
        ObjectMapper mapper = new ObjectMapper();
        for(Map.Entry<String,Object> entry : keyValues.entrySet()){
            String[] cols = entry.getKey().split(":");
            try {
                String value  = mapper.writeValueAsString( entry.getValue());
                put.addColumn(Bytes.toBytes(cols[0]), Bytes.toBytes(cols[1]), Bytes.toBytes( value ));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //System.out.println(put.toString());
        return put;
    }

    public Put generatePut(String rowkey, String columnFamily, Map<String, String> keyValues){
        Put put = new Put(Bytes.toBytes(rowkey));
        for(Map.Entry<String,String> entry : keyValues.entrySet()){
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
        }
        //System.out.println(put.toString());
        return put;
    }

    public boolean isTableExists(TableName tableName) throws IOException {
        boolean result = false;
        Admin admin = HBaseConfig.getConn().getAdmin();
        return admin.tableExists(tableName);
    }

    public Result read(String tableName , String rowkey){
        Get get = new Get(Bytes.toBytes(rowkey));
        //get.addFamily(Bytes.toBytes(cf_create));
        Table table = getTable(getTableName(tableName));
        Result result = null;
        try {
            result = table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        try {
            System.out.println(writer.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getRowKey(Result res)
    {
        Cell[] cells = res.rawCells();
        return new String(CellUtil.cloneRow(cells[0]));
    }

    public String printCell(Result res)
    {
        Cell[] cells = res.rawCells();
        System.out.println(" \n\n print read row start :" + Bytes.toString(CellUtil.cloneRow(cells[0])));
        for (Cell cellItem:
                cells) {



            System.out.println(" read row1 family:" + Bytes.toString(CellUtil.cloneFamily(cellItem))
                    +" val:" + Bytes.toString(CellUtil.cloneValue(cellItem))
                    +" timestamp:" + cellItem.getTimestamp()
                    +" row key:" + Bytes.toString(CellUtil.cloneRow(cellItem))
                    +" qualifier :" + Bytes.toString(CellUtil.cloneQualifier(cellItem))
            );
        }
        return new String(CellUtil.cloneRow(cells[0]));
    }

    /**
     * 检索表中指定数据
     *
     * @param tableName  表名
     * @param filterList 过滤器
     */

    public  ResultScanner getScanner(String tableName, FilterList filterList) {
        try {
            Table table = getTable(getTableName(tableName));
            Scan scan = new Scan();
            scan.setFilter(filterList);
            return table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检索表中指定数据
     *
     * @param tableName   表名
     * @param startRowKey 起始 RowKey
     * @param endRowKey   终止 RowKey
     * @param filterList  过滤器
     */

    public  ResultScanner getScanner(String tableName, String startRowKey, String endRowKey,
                                           FilterList filterList) {
        try {
            Table table = getTable(getTableName(tableName));
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRowKey));
            scan.setStopRow(Bytes.toBytes(endRowKey));
            scan.setFilter(filterList);
            return table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  ResultScanner getScannerWithPage(String tableName, String startRowKey, int pageSize,
                                     FilterList filterList) {
        try {
            Table table = getTable(getTableName(tableName));
            Filter pageFilter = new PageFilter(pageSize);
            filterList.addFilter(pageFilter);

            Scan scan = new Scan();
            byte[] startRowKeyBytes = startRowKey.getBytes();
            byte[] endBytes = new byte[]{0x00};
            if (startRowKeyBytes != null)
            {
                startRowKeyBytes = Bytes.add(startRowKeyBytes,endBytes);
                scan.setStartRow(startRowKeyBytes);
            }

            scan.setFilter(filterList);
            return table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除指定行记录
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     */
    public  boolean deleteRow(String tableName, String rowKey) {
        try {
            Table table = getTable(getTableName(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 删除指定行的指定列
     *
     * @param tableName  表名
     * @param rowKey     唯一标识
     * @param familyName 列族
     * @param qualifier  列标识
     */
    public  boolean deleteColumn(String tableName, String rowKey, String familyName,
                                       String qualifier) {
        try {
            Table table = getTable(getTableName(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
            table.delete(delete);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int count(String tableName)
    {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        String startRowKey = "";
        int totalCnt = 0,pageSize = 1000;
        while (true)
        {
            ResultScanner scanner = this.getScannerWithPage(tableName, startRowKey, pageSize, filterList);
            int cnt = 0;
            if (scanner != null) {
                while (true) {
                    Result res2 = null;
                    try {
                        res2 = scanner.next();
                        if (res2 == null) {
                            break;
                        }
                        startRowKey = this.getRowKey(res2);
                        cnt++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        return  totalCnt;
    }

    public static void main(String[] args) {
        // System.setProperty("HADOOP_USER_NAME","root");
        try {
            HBaseUtils hBaseUtils = new HBaseUtils();
            hBaseUtils.createTable("Student2",new String[]{"a"});
            String tableName = "Student";
            Result res = hBaseUtils.read(tableName,"row5");
            System.out.println(" read row1 res:" + Bytes.toString(res.getRow()));
            hBaseUtils.printCell(res);

            String rowkey = "row";
            String[] baseInfoCols = {"name","age","sex"};
            String[] schoolInfoCols = {"cls","teacher","subject"};
//            long lCur = System.currentTimeMillis();
//            for (int i = 10; i < 10000; i++) {
//                String curRowKey  =rowkey + i;
//                Map<String,Object> curRowDataMap = new HashMap<String, Object>();
//                for (String baseCol:baseInfoCols) {
//                    curRowDataMap.put("baseInfo:" + baseCol,"base_" + baseCol + i);
//                }
//
//                for (String schCol:schoolInfoCols) {
//                    curRowDataMap.put("schoolInfo:" + schCol,"sch_"+schCol + i);
//                }
//                hBaseUtils.write(tableName,curRowKey,curRowDataMap);
//            }
//            System.out.println(" write end time:" + (System.currentTimeMillis() - lCur));


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

            DependentColumnFilter rowfilter = new DependentColumnFilter(
                    Bytes.toBytes("baseInfo"),
                    Bytes.toBytes("sex"),
                    true,
                    CompareFilter.CompareOp.EQUAL,
                    new SubstringComparator("base_sex328"));

            filterList.addFilter(rowfilter);

            ResultScanner scanner = hBaseUtils.getScanner(tableName,"row3282","row3285", filterList);
            if (scanner != null) {

                int cnt = 0;
                while (true)
                {
                    Result res2 = scanner.next();
                    if (res2 == null)
                    {
                        break;
                    }
                    hBaseUtils.printCell(res2);
                    cnt++;
                }
                System.out.println(" search cnt:" + cnt);

//                scanner.forEach(result -> System.out.println(Bytes.toString(result.getRow()) + "->" + Bytes
//                        .toString(result.getValue(Bytes.toBytes(tableName), Bytes.toBytes("name")))));
                scanner.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
