package com.tpw.goo.service.impl;

import com.tpw.goo.bean.PageDto;
import com.tpw.goo.bean.UserMingxi;
import com.tpw.goo.dao.MingxiNewHbaseDao;
import com.tpw.goo.dao.MingxiNewPhoenixDao;
import com.tpw.goo.dao.UserMingXiMapper;
import com.tpw.goo.phoniex_dao.UserMingXiPhoniexMapper;
import com.tpw.goo.service.IUserMingXiService;
import com.tpw.goo.util.MyDateUtil;
import com.tpw.goo.util.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class UserMingXiServiceImpl implements IUserMingXiService {

//    private static Logger logger = LogManager.getLogger(UserMingXiServiceImpl.class);
    private static Log logger = LogFactory.getLog(UserMingXiServiceImpl.class);

    @Autowired
    protected UserMingXiMapper userMingXiMapper;

    @Autowired
    protected MingxiNewHbaseDao mingxiNewHbaseDao;

    @Autowired
    protected MingxiNewPhoenixDao mingxiNewPhoenixDao;

    @Autowired
    protected UserMingXiPhoniexMapper userMingXiPhoniexMapper;

    @Override
    public boolean syncMysqlDataToHBase() {
        logger.info(" begin" );

        long nMinCreateTime = mingxiNewPhoenixDao.getMinCreateTime();
        long nMaxCreateTime = mingxiNewPhoenixDao.getMaxCreateTime();
        int offset =0,pageSize = 1000;
        int totalCnt = 0;
        long lCur = System.currentTimeMillis();
        int loopCnt = 0;
        String lastCreateTime = "";
        String cacheKey = "MingXi:lastCreateTime";
        String val = (String) RedisUtil.get(cacheKey);
        if (val != null)
        {
            lastCreateTime = val;
        }else{
            //lastCreateTime = "2020-07-05 16:33:01";
            lastCreateTime = MyDateUtil.formatDate(new Date(nMaxCreateTime),null);
        }


         while (true)
         {
             List<UserMingxi>  userMingxis =  userMingXiMapper.selectAll(/*offset*/ 0,pageSize,lastCreateTime);
             totalCnt += userMingxis.size();
             offset += pageSize;

             mingxiNewHbaseDao.WriteData(userMingxis);
            // mingxiNewPhoenixDao.insert(userMingxis.get(0));
//             mingxiNewPhoenixDao.batchInsert(userMingxis);
             if (userMingxis.size() >0 )
             {
                 lastCreateTime = MyDateUtil.formatDate(userMingxis.get(userMingxis.size()-1).getCreate_time(),null);
             }

             logger.info(" cnt:" + userMingxis.size() + " totalCnt:" + totalCnt
             +" lastCreateTime:"+lastCreateTime);
             RedisUtil.set(cacheKey,lastCreateTime,-1);
             loopCnt++;
             if (loopCnt >= 10)
             {
                 break;
             }

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

    @Override
    public PageDto<UserMingxi> list(int uid, int pageNo, int pageSize) {
        logger.info(" begin");
        PageDto<UserMingxi> userMingxiPageDto = new PageDto<>();
        List<UserMingxi> userMingxiList =  userMingXiPhoniexMapper.selectAll(uid,pageNo,pageSize);
        userMingxiPageDto.setData(userMingxiList);
        return  userMingxiPageDto;

    }

    @Override
    public boolean initMingXiTable() {
        mingxiNewHbaseDao.initTable();
        return  true;
    }
}
