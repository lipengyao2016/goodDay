package com.tpw.goo.test;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PhoenixUtils {

    public int update(String updateSql) throws SQLException {
        Statement statement = PhoenixConfig.getConn().createStatement();
        int nRet = statement.executeUpdate(updateSql);
        PhoenixConfig.getConn().commit();
        statement.close();
        return nRet;
    }

    public ResultSet query(String querySql) throws SQLException {
        Statement statement = PhoenixConfig.getConn().createStatement();
        ResultSet resultSet = statement.executeQuery(querySql);
        statement.close();
        return resultSet;
    }
}
