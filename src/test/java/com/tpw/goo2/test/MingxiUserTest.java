package com.tpw.goo2.test;

import com.tpw.goo.bean.PageDto;
import com.tpw.goo.bean.UserMingxi;
import com.tpw.goo.service.IUserMingXiService;
import com.tpw.goo.test.HBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MingxiUserTest extends BaseUnitTest {

    @Resource
    IUserMingXiService userMingXiService;


    @Test
    public void syncMysqlDataToHBase() throws IOException {

        userMingXiService.syncMysqlDataToHBase();
    }

    @Test
    public void list() throws IOException {

        PageDto<UserMingxi> userMingxiPageDto = userMingXiService.list(10408531,1,10);
        System.out.println("end.");
    }


}
