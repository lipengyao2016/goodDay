package com.tpw.goo.service;

import com.tpw.goo.bean.User;

public interface IUserService {
	/**
	 * 
	 * @Description: 根据用户登陆名查询用户信息
	 * @author tianpengw 
	 * @param userName
	 * @return
	 */
	public User findUserByLoginName(String userLoginName);
	/**
	 * 
	 * @Description:根据用户编号查询用户信息 
	 * @author tianpengw 
	 * @param userId
	 * @return
	 */
	public User findUserById(String userId);
}
