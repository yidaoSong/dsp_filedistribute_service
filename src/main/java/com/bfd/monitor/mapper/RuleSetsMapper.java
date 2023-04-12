package com.bfd.monitor.mapper;

import java.util.List;

import com.bfd.monitor.bean.RuleSets;

/**
 * 
 * @ClassName:     RuleSetsMapper
 * @Description:规则集Mapper
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午11:08:41
 *
 * 
 
 */
public interface RuleSetsMapper {
	/*
	 * 根据rule获取set信息
	 */
	RuleSets queryRuleSetsByRule(int id);

	/**
	 * 查询所有启动的规则集
	 */
	List<RuleSets> queryStartRuleSets();

	/**
	 * 根据主键查询结果集
	 */
	RuleSets queryRuleSetsById(int id);
	
	/**
	 * 查询数据库系统时间
	 * @return
	 */
	String querySystemTime();
}
