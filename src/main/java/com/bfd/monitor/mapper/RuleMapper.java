package com.bfd.monitor.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleLine;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @ClassName:     RuleMapper
 * @Description:规则Mapper
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午11:02:34
 *
 * 
 
 */
public interface RuleMapper {
	
	/**
	 * 
	 * @Title: updateRuleSwitchBySet
	 * @Description: 修改规则状态
	 * @param: @param switchOf
	 * @param: @param id
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int updateRuleSwitchBySet(int switchOf,int id);
	
	/**
	 * 
	 * @Title: updateRuleSetSwitch
	 * @Description: 修改规则集状态
	 * @param: @param switchOf
	 * @param: @param id
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int updateRuleSetSwitch(int switchOf,int id);
	
	/**
	 * 
	 * @Title: deleteRuleLineByRuleId
	 * @Description: 根据规则主键删除规则对应的线路
	 * @param: @param ruleId
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int deleteRuleLineByRuleId(int ruleId);
	
	/**
	 * 
	 * @Title: queryRuleById
	 * @Description: 根据主键查询规则信息
	 * @param: @param id
	 * @param: @return   
	 * @return: Rule   
	 * @throws
	 
	 */
	public Rule queryRuleById(int id);
	
	/**
	 * 
	 * @Title: checkRuleByName
	 * @Description: 根据规则名称查询规则列表
	 * @param: @param name
	 * @param: @return   
	 * @return: List<Rule>   
	 * @throws
	 
	 */
	public List<Rule> checkRuleByName(String name);
	
	/**
	 * 
	 * @Title: updateRuleSwitch
	 * @Description: 修改规则状态
	 * @param: @param switchOf
	 * @param: @param id
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int updateRuleSwitch(int switchOf,int id);
	
	/**
	 * 
	 * @Title: queryRLByRuleName
	 * @Description: 查询线路信息根据规则名称
	 * @param: @param ruleName
	 * @param: @param relation
	 * @param: @return   
	 * @return: List<RuleLine>   
	 * @throws
	 
	 */
	public List<RuleLine> queryRLByRuleName(String ruleName,String relation);
	
	/**
	 * 
	 * @Title: updateRuleLine
	 * @Description: 修改规则线路
	 * @param: @param list
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int updateRuleLine(List<RuleLine> list);
	
	/**
	 * 
	 * @Title: deleteRuleLineById
	 * @Description: 根据规则主键删除规则对应的线路
	 * @param: @param id   
	 * @return: void   
	 * @throws
	 
	 */
	public void deleteRuleLineById(int id);
	
	/**
	 * 
	 * @Title: queryRuleLineByRelation
	 * @Description: 根据对应关系查询规则对应的线路
	 * @param: @param map
	 * @param: @return   
	 * @return: List<RuleLine>   
	 * @throws
	 
	 */
	public List<RuleLine> queryRuleLineByRelation(Map<String, Integer> map);
	
	/**
	 * 
	 * @Title: insertDistributeLog
	 * @Description: 插入稿件分发日志
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int insertDistributeLog(Manuscript manuscript);

	/**
	 * 
	 * @Title: queryErrorCount
	 * @Description: 查询日志分发错误数量
	 * @param: @param setId
	 * @param: @param fileName
	 * @param: @param errorType
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int queryErrorCount(int setId, String fileName, String errorType);

	/**
	 * 
	 * @Title: addError
	 * @Description: 添加分发错误日志
	 * @param: @param fileName
	 * @param: @param errorType
	 * @param: @param setId   
	 * @return: void   
	 * @throws
	 
	 */
	public void addError(String fileName, String errorType, int setId);

	/**
	 * 
	 * @Title: deleteExceptionByDay
	 * @Description: 删除异常数据
	 * @param: @param day   
	 * @return: void   
	 * @throws
	 
	 */
	public void deleteExceptionByDay(int day);
	
	/**
	 * 
	 * @Title: queryRuleBySet
	 * @Description:查询规则信息
	 * @param: @param rule
	 * @param: @return   
	 * @return: List<Rule>   
	 * @throws
	 
	 */
	public List<Rule> queryRuleBySet(Rule rule);
	
	/**
	 * 
	 * @Title: queryRLByRuleId
	 * @Description: 根据条件查询规则对应的线路
	 * @param: @param map
	 * @param: @return   
	 * @return: List<RuleLine>   
	 * @throws
	 
	 */
	public List<RuleLine> queryRLByRuleId(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: queryRuleBySetNoStatus
	 * @Description: 根据状态查询规则信息
	 * @param: @param rule
	 * @param: @return   
	 * @return: List<Rule>   
	 * @throws
	 
	 */
	public List<Rule> queryRuleBySetNoStatus(Rule rule);

	/**
	 * 根据规则集查询文件到文件分发规则
	 * @param ruleDemo
	 * @return
	 */
	public List<Rule> queryRuleAllBySet(Rule ruleDemo);

	/**
	 * 
	 * @Title: insertDistributeRuleSetLog
	 * @Description: 插入每篇稿件分发日志
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int insertDistributeRuleSetLog(Manuscript manuscript);

	/**
	 * 请理半年前的日志
	 * @param time
	 */
    void cleanDistributeLog(@Param("time") String time);

	void cleanDistributeRulesetLog(@Param("time") String time);

	void cleanRuleMonitorException(@Param("time") String time);
}
