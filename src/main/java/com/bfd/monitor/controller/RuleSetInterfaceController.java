package com.bfd.monitor.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.common.pojo.JsonResult;
import com.bfd.monitor.service.RuleService;
import com.bfd.monitor.service.RuleSetsService;
import com.bfd.monitor.utils.DistributeUtil;
import com.bfd.monitor.utils.SpringContextUtil;

/**
 * 
 * @ClassName:     RuleSetInterfaceController
 * @Description:规则启停接口实现类
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午10:19:20
 *
 * 
 
 */
@RestController
@RequestMapping(produces = {"application/json;charset=UTF-8"})
@ResponseBody
public class RuleSetInterfaceController {
//	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

	@Autowired
    private RuleSetsService ruleSetsService;

	
	/**
	 * 
	 * @Title: setRuleService
	 * @Description: 设置规则服务
	 * @param: @param ruleService   
	 * @return: void   
	 * @throws
	 
	 */
//	@SuppressWarnings("static-access")
//	public void setRuleService(RuleService ruleService){
//		this.ruleService = ruleService;
//	}
	
	/**
	 * 
	 * @Title: startRule
	 * @Description: 规则启动接口
	 * @param: @param ruleSet
	 * @param: @return   
	 * @return: JsonResult   
	 * @throws
	 
	 */
	@RequestMapping(value = "/serviceStartRule")
	public JsonResult startRule(@RequestParam(value = "ruleSet", required = true) String ruleSet) {
		//接口返回参数，默认设置为启动成功
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode("1");
		jsonResult.setMessage("启动成功");
		
		RuleSets set = new RuleSets();
		try {
			set = JSONObject.parseObject(ruleSet, RuleSets.class);
		} catch (Exception e) {
			jsonResult.setCode("0");
			jsonResult.setMessage("规则集" + set.getId() + "启动失败，原因为：" + e.getMessage());
			return jsonResult;
		}
		
		try {
			// 创建校验规则集文件夹没有权限则线程不启动
			String errorMessage = DistributeUtil.checkSetDirectoryError(set);
			if (StringUtils.isNotBlank(errorMessage)) {// 规则集目录没有权限
				jsonResult.setCode("0");
				jsonResult.setMessage("规则集" + set.getSetName() + errorMessage + "，无法启动。");
			} else {
				// 判断规则集下边的规则中下发目录是否有无权限的情况
				String ruleStr = DistributeUtil.checkRuleDirectoryErrorNoStatus(set.getId(), set.getSetName());

				if (StringUtils.isNotBlank(ruleStr)) {
					jsonResult.setCode("0");
					jsonResult.setMessage("规则集" + set.getSetName() + "下发的规则目录" + ruleStr + "没有相关文件夹的操作权限，无法启动。");
				} else {
					//启动了任务
					StartStop.startScheduler("rule_" + set.getId(), set.getQuartz());
				}
			}
		} catch (SchedulerException e) {
			jsonResult.setCode("0");
			jsonResult.setMessage("规则集" + set.getId() + "启动失败，原因为：" + e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 
	 * @Title: stopRule
	 * @Description: 停用规则
	 * @param: @param ruleSet
	 * @param: @return   
	 * @return: JsonResult   
	 * @throws
	 */
	@RequestMapping(value = "/serviceStopRule")
	public JsonResult stopRule(@RequestParam(value = "ruleSet", required = true)
	String ruleSet) {
		//接口返回参数，默认设置为启动成功
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode("1");
		jsonResult.setMessage("停止成功");
		try{
			RuleSets set = JSONObject.parseObject(ruleSet, RuleSets.class);
			StartStop.removeJob("rule_" +set.getId());
		}catch(Exception e){
			jsonResult.setCode("0");
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 
	 * @Title: distributeMonitor
	 * @Description: 异常监控接口
	 * @param: @return   
	 * @return: JsonResult   
	 * @throws
	 
	 */
	@RequestMapping(value = "/distributeMonitor")
    public JsonResult distributeMonitor() {
	    JsonResult result = new JsonResult();
	    //查询时间
	    Map<String, String> map = ruleSetsService.distributeMonitor();
	    if (StringUtils.isNotBlank(map.get("time"))) {
            result.setCode("0");
            result.setMessage("访问数据库成功");
            result.setResult(map.get("time"));
        }else {
            result.setCode("2");
            result.setMessage("访问数据库失败");
            result.setResult(map.get("errorMessage"));
        }
	    return result;
	}
}