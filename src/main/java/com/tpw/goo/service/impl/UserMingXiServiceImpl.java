package com.tpw.goo.service.impl;

import com.tpw.goo.bean.UserMingxi;
import com.tpw.goo.dao.MingxiNewHbaseDao;
import com.tpw.goo.dao.UserMingXiMapper;
import com.tpw.goo.service.IUserMingXiService;
import com.tpw.goo.util.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserMingXiServiceImpl implements IUserMingXiService {

    private static Logger logger = LogManager.getLogger(UserMingXiServiceImpl.class);

    @Autowired
    protected UserMingXiMapper userMingXiMapper;

    @Autowired
    protected MingxiNewHbaseDao mingxiNewHbaseDao;

    @Override
    public boolean syncMysqlDataToHBase() {
        logger.info(" begin" );

        mingxiNewHbaseDao.initTable();
       // return  true;
        int offset =0,pageSize = 1000;
        int totalCnt = 0;
        long lCur = System.currentTimeMillis();
        int loopCnt = 0;
         while (true)
         {
             List<UserMingxi>  userMingxis =  userMingXiMapper.selectAll(offset,pageSize);
             totalCnt += userMingxis.size();
             offset += pageSize;
             logger.info(" cnt:" + userMingxis.size() + " totalCnt:" + totalCnt);
             mingxiNewHbaseDao.WriteData(userMingxis);
             if (loopCnt >= 50)
             {
                 break;
             }
             loopCnt++;
             if (loopCnt%10 ==0)
             {
                 logger.info(" per 10 ci.. totalCnt:" + totalCnt + " loopCnt:" + loopCnt + " tm:" + (System.currentTimeMillis()-lCur) );
                 lCur = System.currentTimeMillis();
             }

             if (userMingxis.size() < pageSize)
             {
                 break;
             }

         }
        logger.info(" end.. totalCnt:" + totalCnt  + " loopCnt:" + loopCnt + " tm:" + (System.currentTimeMillis()-lCur) );
         return  true;
    }
}
