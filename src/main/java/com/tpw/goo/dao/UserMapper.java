package com.tpw.goo.dao;

import com.tpw.goo.base.IBaseDao;
import com.tpw.goo.bean.User;

public interface UserMapper extends IBaseDao<User>{
    
	/**
	 * 
	 * @Description: 根据用户登陆名查询用户信息
	 * @author tianpengw 
	 * @param userName
	 * @return
	 */
	public User findUserByLoginName(String userLoginName);
}