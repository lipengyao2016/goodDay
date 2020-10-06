package com.tpw.goo.test;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PhoenixConfig {
    static Connection conn = null;

    static {
        // 加载数据库驱动
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            System.out.println("load driver ok.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
         * 指定数据库地址,格式为 jdbc:phoenix:Zookeeper 地址
         * 如果 HBase 采用 Standalone 模式或者伪集群模式搭建，则 HBase 默认使用内置的 Zookeeper，默认端口为 2181
         */
        try {
            conn = DriverManager.getConnection("jdbc:phoenix:47.112.175.168:2181");
            System.out.println("getConnection ok.");
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement createStm() throws SQLException {
        return PhoenixConfig.getConn().createStatement();
    }

    public static void closeStm(Statement statement)  {
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
