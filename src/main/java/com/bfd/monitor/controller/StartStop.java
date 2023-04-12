/**  
* StartStop.java,主要用于   
* @author wangyuejiao  
* @date 2019年5月22日  
*/  
package com.bfd.monitor.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.service.RuleService;
import com.bfd.monitor.utils.DistributeUtil;
import com.bfd.monitor.utils.SpringContextUtil;

/**  
 * StartStop 
 * @author wangyuejiao  
 * @date 2019年5月22日  
 */
public class StartStop {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

	private static RuleService ruleService = (RuleService)SpringContextUtil.getBean("RuleService");
	/**
	 * 
	 * @Title: startScheduler
	 * @Description: 启动线程
	 * @param: @param id
	 * @param: @param quartz
	 * @param: @throws SchedulerException   
	 * @return: void   
	 * @throws
	 
	 */
	public static  void startScheduler(String id, String quartz) throws SchedulerException {
		//程序调度器
		Scheduler scheduler = gSchedulerFactory.getScheduler();
		JobDetail job = null;
		if (StringUtils.isNotBlank(id) && id.startsWith("rule")) {
			//建立任务并启动
			job = JobBuilder.newJob(MyRuleJob.class).withIdentity(id, id).build();
			
			String setId = id.replace("rule_", "").trim();
			//根据主键查询规则集信息
			int nu = Integer.parseInt(setId);
			RuleSets ruleSet = ruleService.queryRuleSetsById(nu);//已经判断加载方式
			//从规则集中获取所有分发规则
			List<Rule> rulelistFtoF = DistributeUtil.getRuleBySetFtoF(ruleSet);//文件对文件 ok
			job.getJobDataMap().put("set",ruleSet );
			job.getJobDataMap().put("rule",rulelistFtoF );
		} 
		String[] quartzs = quartz.split(",");
		for (int i = 0; i < quartzs.length; i++) {
			quartz = quartzs[i];
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(id, id)
					.withSchedule(CronScheduleBuilder.cronSchedule(quartz)).build();
			//先判断当前任务是否已启动，若已启动则先停止
			if(scheduler.checkExists(job.getKey())){
				removeJob(id);
			}
			scheduler.scheduleJob(job, trigger);
		}
		scheduler.start();

	}

	/**
	 * 
	 * @Title: removeJob
	 * @Description: 结束线程任务
	 * @param: @param jobNameId   
	 * @return: void   
	 * @throws
	 
	 */
	public static void removeJob(String jobNameId) {
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobNameId, jobNameId);
			scheduler.unscheduleJob(triggerKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
