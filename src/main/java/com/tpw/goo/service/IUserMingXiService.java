package com.tpw.goo.service;

import com.tpw.goo.bean.PageDto;
import com.tpw.goo.bean.User;
import com.tpw.goo.bean.UserMingxi;

public interface IUserMingXiService {

	public boolean syncMysqlDataToHBase();

	public PageDto<UserMingxi> list(int uid, int pageNo, int pageSize);
}
