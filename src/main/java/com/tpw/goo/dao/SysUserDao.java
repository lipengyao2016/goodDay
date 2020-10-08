package com.tpw.goo.dao;

import com.tpw.goo.bean.User;
import com.tpw.goo.util.MybatisDruidUtil;
import com.tpw.goo.util.MybatisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class SysUserDao {

    private Log log = LogFactory.getLog(SysUserDao.class);
    public User getUser(String userId)
    {
//        SqlSession sqlSession =  MybatisUtil.openSession();
        SqlSession sqlSession =  MybatisDruidUtil.openSession();
        User user = sqlSession.selectOne("com.tpw.goo.dao.UserMapper.selectByPrimaryKey",userId);
        log.debug(user);
        return  user;
    }
}
