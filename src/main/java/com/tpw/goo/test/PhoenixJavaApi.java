package com.tpw.goo.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhoenixJavaApi {
    public static void main(String[] args) throws Exception {

        // 加载数据库驱动
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        System.out.println("load driver ok.");
        /*
         * 指定数据库地址,格式为 jdbc:phoenix:Zookeeper 地址
         * 如果 HBase 采用 Standalone 模式或者伪集群模式搭建，则 HBase 默认使用内置的 Zookeeper，默认端口为 2181
         */
        Connection connection = DriverManager.getConnection("jdbc:phoenix:47.112.175.168:2181");

        System.out.println("get  connection ok.");
        String sql = "SELECT * FROM us_population";
        sql = "SELECT state ,count(city) as cityCnt,sum(population) as popuTotal FROM us_population GROUP BY state ORDER BY sum(population) DESC";
        PreparedStatement statement = connection.prepareStatement(sql);
        System.out.println("get  PreparedStatement ok.");
        ResultSet resultSet = statement.executeQuery();
        System.out.println("get  executeQuery ok.");
        int nCount = 0;
        while (resultSet.next()) {
            System.out.println("state:" + resultSet.getString("state") +",cityCnt:"
                    +resultSet.getString("cityCnt") + " ,popuTotal:"
                    + resultSet.getInt("popuTotal"));
            nCount++;
        }
        System.out.println("get  result nCount:" +nCount);
        statement.close();
        connection.close();
    }
}
