package com.bfd.monitor.service;

import java.util.List;
import java.util.Map;

import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleLine;
import com.bfd.monitor.bean.RuleSets;

/**
 * 规则Service
 * @author Administrator
 *
 */
public interface RuleService {
	/**
	 * 
	 * @Title: getRuleLine
	 * @Description: 根据条件查询规则信息
	 * @param: @param rule
	 * @param: @return   
	 * @return: Rule   
	 * @throws
	 
	 */
	public Rule getRuleLine(Rule rule);
	
	/**
	 * 
	 * @Title: queryRuleSetsById
	 * @Description: 根据主键查询规则集信息
	 * @param: @param id
	 * @param: @return   
	 * @return: RuleSets   
	 * @throws
	 
	 */
	public RuleSets queryRuleSetsById(int id);

	/**
	 * 修改规则状态
	 * @param switchOf
	 * @param id
	 */
	public void updateRuleSwitch(int switchOf,int id);
	
	/**
	 * 查询规则对应的线路栏目
	 * @param ruleName
	 * @return
	 */
	Map<String, List<RuleLine>> queryRLByName(String ruleName);
	
	/**
	 * 
	 * @Title: insertDistributeLog
	 * @Description: 插入分发日志
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
//	public int insertDistributeLog(Manuscript manuscript);
	
	/**
	 * 
	 * @Title: insertDistributeRuleSetLog
	 * @Description: 插入每篇稿件分发日志
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
//	public int insertDistributeRuleSetLog(Manuscript manuscript);
	
	
	/**
	 * 
	 * @Title: insertLog
	 * @Description: 插入日志表
	 * @param: @param flag
	                      插入规则集日志还是规则日志，0：规则集日志，1：规则日志
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int insertLog(int flag,Manuscript manuscript) throws Exception;
	
	/**
	 * 
	 * @Title: errorLog
	 * @Description: 错误日志查询
	 * @param: @param ruleSet
	 * @param: @param manuscript   
	 * @return: void   
	 * @throws
	 
	 */
	public void errorLog(RuleSets ruleSet, Manuscript manuscript);
	
	/**
	 * 
	 * @Title: queryRuleBySet
	 * @Description: 根据条件查询规则信息
	 * @param: @param rule
	 * @param: @return   
	 * @return: List<Rule>   
	 * @throws
	 
	 */
	public List<Rule> queryRuleBySet(Rule rule);

	/**
	 * 根据条件查询未启动的规则列表
	 * @param ruleDemo
	 * @return
	 */
	public List<Rule> queryRuleBySetNoStatus(Rule ruleDemo);

	/**
	 * 根据规则集主键查询文件到文件的所有分发规则
	 * @param ruleDemo
	 * @return
	 */
	public List<Rule> queryRuleAllBySet(Rule ruleDemo);

	/**
	 * 请理半年前的日志
	 * @param time
	 */

    void cleanLogHalfYear(String time);
}
