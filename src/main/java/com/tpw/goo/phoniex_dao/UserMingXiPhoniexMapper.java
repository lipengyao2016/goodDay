package com.tpw.goo.phoniex_dao;

import com.tpw.goo.base.IBaseDao;
import com.tpw.goo.bean.UserMingxi;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;
import java.util.Map;


public interface UserMingXiPhoniexMapper extends IBaseDao<UserMingxi>{
	public List<UserMingxi> selectAll(@Param("uid") Integer uid,@Param("offset") Integer offset, @Param("limit") Integer limit);

	@Select("select uid, count(relate_id) as orderCnt,sum(money) as totalMoney from UserMingXiPhoenix6 where uid = #{uid} group by uid")
	public Map<String,Object> staticsIncome(@Param("uid") Integer uid);
}