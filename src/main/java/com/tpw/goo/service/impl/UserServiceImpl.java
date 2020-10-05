package com.tpw.goo.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.tpw.goo.bean.User;
import com.tpw.goo.dao.UserMapper;
import com.tpw.goo.service.IUserService;

/**
 * 
 * @ClassName: UserServiceImpl 
 * @Description: 用户服务实现类
 * @author tianpengw 
 * @date 2017年12月7日 下午5:11:04 
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserMapper userDao;

	/** 
	 * @see com.tpw.goo.service.IUserService#findUserByLoginName(java.lang.String)
	 */
	public User findUserByLoginName(String userLoginName) {
		return userDao.findUserByLoginName(userLoginName);
	}

	/** 
	 * @see com.tpw.goo.service.IUserService#findUserById(java.lang.String)
	 */
	public User findUserById(String userId) {
		return userDao.selectByPrimaryKey(userId);
	}

}
