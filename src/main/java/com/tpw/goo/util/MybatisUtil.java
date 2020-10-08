package com.tpw.goo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    //将sqlsession工厂定义在外面，方便获取sqlsession方法，
    private static SqlSessionFactory sqlSessionFactory ;
    private static  Log log = LogFactory.getLog(MybatisUtil.class);

    //静态方法，调用直接创建
    static{
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream("mybatis-local-config.xml");


            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(in);
            log.debug(sqlSessionFactory);
            log.debug(" end." );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //打开sqlsession
    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }
}
