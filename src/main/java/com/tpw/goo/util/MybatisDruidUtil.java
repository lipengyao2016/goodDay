package com.tpw.goo.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class MybatisDruidUtil {
    //将sqlsession工厂定义在外面，方便获取sqlsession方法，
    private static SqlSessionFactory sqlSessionFactory ;
    private static  Log log = LogFactory.getLog(MybatisDruidUtil.class);

    //静态方法，调用直接创建
    static{
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8");
            dataSource.setUsername("root");
            dataSource.setPassword("123456");
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);

            sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
            sqlSessionFactoryBean.setMapperLocations(new Resource[]{new ClassPathResource("mapping/UserMapper.xml")});
        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //打开sqlsession
    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }
}
