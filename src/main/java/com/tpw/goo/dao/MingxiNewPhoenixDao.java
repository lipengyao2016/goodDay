package com.tpw.goo.dao;


import com.tpw.goo.bean.UserMingxi;
import com.tpw.goo.service.impl.UserMingXiServiceImpl;
import com.tpw.goo.test.HBaseUtils;
import com.tpw.goo.test.PhoenixConfig;
import com.tpw.goo.test.PhoenixUtils;
import com.tpw.goo.util.ReflectUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MingxiNewPhoenixDao {

    public String  table = "UserMingXiPhoenix6";
    public Map<String,String[]> colFamily = new HashMap<String,String[]>();

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(MingxiNewPhoenixDao.class);

    @Resource
    PhoenixUtils phoenixUtils;

    public MingxiNewPhoenixDao() {
    }


    public boolean insert(UserMingxi userMingxis)
    {
        Timestamp createTime = new Timestamp(userMingxis.getCreate_time().getTime());

        String insertSql = "UPSERT INTO "+table+" VALUES ("+
                userMingxis.getUid()+",'"+
                userMingxis.getShijian()+"',"+
                userMingxis.getCreate_time().getTime()+","+
                userMingxis.getRelate_id()+","+
                userMingxis.getTeam_uid()+","+
                userMingxis.getTrade_uid()+",'"+
                userMingxis.getTrade_id_former()+"',"+
                userMingxis.getMoney()+
                ")";
        try {
            logger.info(insertSql);
            System.out.println(insertSql);
            phoenixUtils.update(insertSql);
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }

    public boolean batchInsert(List<UserMingxi> userMingxiList)
    {
        Statement statement = null;
        boolean bSuced = false;
        try {
            statement = PhoenixConfig.getConn().createStatement();
            for (UserMingxi userMingxis: userMingxiList) {
                Timestamp createTime = new Timestamp(userMingxis.getCreate_time().getTime());

                String insertSql = "UPSERT INTO "+table+" VALUES ("+
                        userMingxis.getUid()+",'"+
                        userMingxis.getShijian()+"',"+
                        userMingxis.getCreate_time().getTime()+","+
                        userMingxis.getRelate_id()+","+
                        userMingxis.getTeam_uid()+","+
                        userMingxis.getTrade_uid()+",'"+
                        userMingxis.getTrade_id_former()+"',"+
                        userMingxis.getMoney()+
                        ")";
                int nRet = statement.executeUpdate(insertSql);
                logger.info(insertSql);
                System.out.println(insertSql);

            }
            PhoenixConfig.getConn().commit();
            bSuced = true;
        } catch (SQLException e) {
            e.printStackTrace();
            bSuced = false;
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  bSuced;
    }

    public long getMinCreateTime()
    {
        long minCreateTime = 0;
        String sql = "select min(create_time) as min_create_time from UserMingXiPhoenix6";
        Statement statement = null;
        try {
            statement = PhoenixConfig.createStm();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
                minCreateTime = resultSet.getLong("min_create_time");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            PhoenixConfig.closeStm(statement);
        }
        return  minCreateTime;
    }

    public long getMaxCreateTime()
    {
        long maxCreateTime = 0;
        String sql = "select max(create_time) as max_create_time from UserMingXiPhoenix6";
        Statement statement = null;
        try {
            statement = PhoenixConfig.createStm();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
                maxCreateTime = resultSet.getLong("max_create_time");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            PhoenixConfig.closeStm(statement);
        }
        return  maxCreateTime;
    }
}
