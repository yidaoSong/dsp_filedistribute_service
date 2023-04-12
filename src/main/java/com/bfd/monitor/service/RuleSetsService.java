package com.bfd.monitor.service;

import java.util.List;
import java.util.Map;

import com.bfd.monitor.bean.RuleSets;

/**
 * 
 * @ClassName:     RuleSetsService
 * @Description:规则集服务
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午11:11:04
 *
 * 
 
 */
public interface RuleSetsService {
	
	/**
	 * 查询所有启动的规则集
	 */
	List<RuleSets> queryStartRuleSets();

	/**
	 * 根据主键查询结果集信息
	 * @param id
	 * @return
	 */
	RuleSets queryRuleSetsById(String id);

	/**
	 * 分发监控查询数据库
	 * @return
	 */
	Map<String, String> distributeMonitor();
}