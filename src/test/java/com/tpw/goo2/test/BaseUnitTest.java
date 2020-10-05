package com.tpw.goo2.test;




import javafx.application.Application;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml",	"classpath:spring-mybatis.xml",
        "classpath:spring-redis.xml"})
//@WebAppConfiguration
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ImportAutoConfiguration(classes = {TeamServices.class, SettleUserWithBLOBs.class, SettleUserVO.class})
public class BaseUnitTest {


}
