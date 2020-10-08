package com.tpw.goo.phoniex_dao;

import com.tpw.goo.base.IBaseDao;
import com.tpw.goo.bean.UserMingxi;
import org.apache.ibatis.annotations.Param;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;


public interface UserMingXiPhoniexMapper extends IBaseDao<UserMingxi>{
	public List<UserMingxi> selectAll(@Param("uid") Integer uid,@Param("offset") Integer offset, @Param("limit") Integer limit);
}