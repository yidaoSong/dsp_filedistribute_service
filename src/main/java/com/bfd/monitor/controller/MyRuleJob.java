package com.bfd.monitor.controller;

import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.distribute.DistributeService;
import com.bfd.monitor.utils.LogType;
import com.bfd.monitor.utils.LogUtil;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName:     MyRuleJob
 * @Description:定时任务
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午9:54:17
 *
 * 
 
 */
@DisallowConcurrentExecution
public class MyRuleJob implements Job {
	
	/**
	 * 
	 * <p>Title: execute</p>
	 * <p>Description: 任务执行</p>
	 * @param context
	 * @throws JobExecutionException
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取任务名称
		String jobName = context.getJobDetail().getKey().getName();
		//时间格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		LogUtil.getLogger(LogType.Ope).info("任务ID(Key):" + jobName + " 正在执行，执行时间: " + dateFormat.format(new Date()));
		// System.out.println("任务ID(Key):" + jobName + " 正在执行，执行时间: " + dateFormat.format(new Date()));
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();

			//获取规则集
			RuleSets set = (RuleSets) dataMap.get("set");
			//获取规则
			List<Rule> rulelistFtoF =(List<Rule>) dataMap.get("rule");
			//分发
			DistributeService.distribute(jobName,set,rulelistFtoF);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}