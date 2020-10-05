package com.tpw.goo.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class HBaseConfig {
    static Configuration conf = null;
    static Connection conn = null;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "47.112.175.168");
        conf.set("hbase.zookeeper.property.client", "2181");
        try{
            conn = ConnectionFactory.createConnection(conf);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  static Connection getConn()
    {
        return  conn;
    }

    public static void close() {
        try {
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
