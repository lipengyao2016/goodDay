package com.tpw.goo.dao;


import com.tpw.goo.bean.PageDto;
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
import java.util.*;

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

    public int getCount(int uid)
    {
        int totalCnt = 0;
        String sql = "select  count(*) as totalCnt from UserMingXiPhoenix6 where uid = " + uid;
        Statement statement = null;
        try {
            statement = PhoenixConfig.createStm();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
                totalCnt = resultSet.getInt("totalCnt");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            PhoenixConfig.closeStm(statement);
        }
        return  totalCnt;
    }

    public PageDto<UserMingxi> listData(int uid, int pageNo, int pageSize)
    {
        long maxCreateTime = 0;
        int totalCnt= this.getCount(uid);
        PageDto<UserMingxi> userMingxiPageDto = new PageDto<>();
        userMingxiPageDto.setTotalCnt(totalCnt);
        userMingxiPageDto.setPageSize(pageSize);
        userMingxiPageDto.setCurPageNo(pageNo);
        int pageCnt = totalCnt/pageSize + (totalCnt%pageSize != 0 ? 1 : 0);
        userMingxiPageDto.setTotalPageCnt(pageCnt);


        List<UserMingxi> userMingxiList = new ArrayList<>();
        int offset = (pageNo -1) * pageSize;
        String sql = "select * from UserMingXiPhoenix6 where uid=" + uid + " order by create_time desc limit " + pageSize + " offset " + offset ;
        Statement statement = null;
        try {
            statement = PhoenixConfig.createStm();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                UserMingxi userMingxi = new UserMingxi();
                userMingxi.setUid(resultSet.getInt("uid"));
                userMingxi.setShijian(resultSet.getString("shijian"));
                userMingxi.setCreate_time(new Date(resultSet.getLong("create_time")));
                userMingxi.setRelate_id(resultSet.getInt("relate_id"));
                userMingxi.setTeam_uid(resultSet.getInt("team_uid"));
                userMingxi.setTrade_uid(resultSet.getInt("trade_uid"));
                userMingxi.setTrade_id_former(resultSet.getString("trade_id_former"));
                userMingxi.setMoney(resultSet.getInt("money"));
                userMingxiList.add(userMingxi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            PhoenixConfig.closeStm(statement);
        }
        userMingxiPageDto.setData(userMingxiList);
        return  userMingxiPageDto;
    }
}
