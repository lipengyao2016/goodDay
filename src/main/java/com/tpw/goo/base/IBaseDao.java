package com.tpw.goo.base;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
/**
 * 
 * @ClassName: IBaseDao 
 * @Description: Mapper接口基础类
 * @author tianpengw 
 * @date 2017年12月7日 下午4:35:53 
 * 
 * @param <T>
 */
public interface IBaseDao<T> {
    
	/**
	 * 功能描述: 新增实体  
	 * @param t
	 * @return 
	 * @author 
	 */
	public Integer insert(T t);
	
	/**
	 * 功能描述: 动态新增实体  
	 * @param t
	 * @return 
	 * @author 
	 */
	public Integer insertSelective(T t);
	
	/**
	 * 功能描述: 根据主键ID更新实体  
	 * @param t 
	 * 创建人：
	 */
	public Integer updateByPrimaryKey(T t);
	
	/**
	 * 功能描述: 根据主键ID动态更新实体  
	 * @param t 
	 * 创建人：
	 */
	public Integer updateByPrimaryKeySelective(T t);
	
	/**
	 * 功能描述: 根据ID号删除单个实体 
	 * @param id
	 * @return 
	 * @author 
	 */
	public Integer deleteByPrimaryKey(String id);
	
	/**
	 * 功能描述: 删除实体 
	 * @param t 
	 * @author 
	 */
	public void delete(T t);
	
	/**
	 * 根据ID号查询单个实体
	 * @param obj
	 */
	public T selectByPrimaryKey(String id);
	
	
	/**
	 * 功能描述: 根据实体(查询条件)对象查询 
	 * @param t
	 * @return 
	 * @author 
	 */
	public T selectByAttribute(Object obj);
	
	
	/**
	 * 查询列表（支持分页查询）
	 * @param object
	 * @return
	 */
	public List<T> selectList(Object obj);
	
	/**
	 * 查询记录总个数
	 * @param object
	 * @return
	 */
	public Integer selectCount(Object obj);
	
	/**
	 * 返回要插入的id取值
	 * 
	 * @return
	 */
	public Integer getGeneralId();
	
	/**
	 * 功能描述: 通过ID查找 
	 * @param ids
	 * @return 
	 * @author
	 */
	public List<T> selectByIds(@Param(value="ids")Collection<Integer> ids);
}