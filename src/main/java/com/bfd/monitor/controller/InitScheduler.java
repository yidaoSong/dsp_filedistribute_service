package com.bfd.monitor.controller;

import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.service.RuleService;
import com.bfd.monitor.service.RuleSetsService;
import com.bfd.monitor.utils.DistributeUtil;
import com.bfd.monitor.utils.LogType;
import com.bfd.monitor.utils.LogUtil;
import com.bfd.monitor.utils.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 
 * @ClassName:     InitScheduler
 * @Description:启动服务器初始化处理线程逻辑
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午9:22:28
 *
 * 
 
 */
public class InitScheduler {
	//规则集服务
	private static RuleSetsService ruleSetsService = (RuleSetsService)SpringContextUtil.getBean("RuleSetsService");
	//规则服务
	private static RuleService ruleService = (RuleService)SpringContextUtil.getBean("RuleService");

	/**
	 * 
	 * @Title: startScheduler
	 * @Description: 启动线程
	 * @param:    
	 * @return: void   
	 * @throws
	 
	 */
	public static void startScheduler(){
		// System.out.println("--------初始化监控的线程----------start----------");
		LogUtil.getLogger(LogType.Run).info("--------初始化监控的线程----------start----------");
		//启动分发监控
			//查询所有启动的规则集
			List<RuleSets> setlist = ruleSetsService.queryStartRuleSets();
			try {
//				RuleSetInterfaceController ruleSetInterfaceController = new RuleSetInterfaceController();
//				ruleSetInterfaceController.setRuleService(ruleService);
				
				if(setlist!=null && setlist.size()>0){
					//循环规则
					for (RuleSets ruleSets : setlist) {
						//验证规则集是否可以启动
						String errorMessage = DistributeUtil.checkSetDirectoryError(ruleSets);
						if(StringUtils.isBlank(errorMessage)){
							//验证规则是否可启动
							String ruleStr = DistributeUtil.checkRuleDirectoryError(ruleSets.getId(), ruleSets.getSetName());
							if(StringUtils.isBlank(ruleStr)){
								//启动线程
								StartStop.startScheduler("rule_"+ruleSets.getId(),ruleSets.getQuartz());
							}
						}
					}
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			// System.out.println("--------初始化分发的线程---------end-----------");
			LogUtil.getLogger(LogType.Run).info("--------初始化分发的线程---------end-----------");
	}
}