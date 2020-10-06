package com.tpw.goo.dao;

import com.tpw.goo.base.IBaseDao;
import com.tpw.goo.bean.User;
import com.tpw.goo.bean.UserMingxi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMingXiMapper extends IBaseDao<UserMingxi>{
	public List<UserMingxi> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit,
									  @Param("lastCreateTime") String lastCreateTime);
}