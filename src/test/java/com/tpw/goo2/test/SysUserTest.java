package com.tpw.goo2.test;

import com.tpw.goo.bean.PageDto;
import com.tpw.goo.bean.User;
import com.tpw.goo.bean.UserMingxi;
import com.tpw.goo.dao.SysUserDao;
import com.tpw.goo.service.IUserMingXiService;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

public class SysUserTest extends BaseUnitTest {

    @Resource
    SysUserDao sysUserDao;


    @Test
    public void getUser() throws IOException {

        User user = sysUserDao.getUser("1709121331320000");
        System.out.println("end.");
    }




}
