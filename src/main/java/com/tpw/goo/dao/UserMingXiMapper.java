package com.tpw.goo.dao;

import com.tpw.goo.base.IBaseDao;
import com.tpw.goo.bean.User;
import com.tpw.goo.bean.UserMingxi;

import java.util.List;

public interface UserMingXiMapper extends IBaseDao<UserMingxi>{
	public List<UserMingxi> selectAll(Integer offset, Integer limit);
}