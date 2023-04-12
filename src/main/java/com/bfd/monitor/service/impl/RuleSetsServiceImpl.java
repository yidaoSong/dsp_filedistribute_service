package com.bfd.monitor.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.bfd.monitor.utils.*;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.mapper.RuleSetsMapper;
import com.bfd.monitor.service.RuleSetsService;

/**
 * 
 * @ClassName:     RuleSetsServiceImpl
 * @Description:规则集服务实现类
 * @author:    Android_Robot
 * @date:        2018年5月23日 上午11:17:11
 *
 * 
 
 */
@Service("RuleSetsService")
@SuppressWarnings("all")
public class RuleSetsServiceImpl implements RuleSetsService {

	@Autowired
	RuleSetsMapper ruleSetsMapper;

	/**
	 * 根据主键查询规则集
	 */
	@Override
	public RuleSets queryRuleSetsById(String id) {
        if (Constants.CONFIG_PRIORITY == 1) {
            //从内存中加载配置
            Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
            List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
            Optional<RuleSets> selectedRuleSets = ruleSetsList.stream().filter(ruleSets -> Objects.equals(ruleSets.getId(), id)).findFirst();
            return selectedRuleSets.get();
        }else{
			try {
				return ruleSetsMapper.queryRuleSetsById(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				//从内存中加载配置
				LogUtil.getLogger(LogType.Beh).info("数据库此时无法使用，将从配置文件中加载数据，时间"+DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
				Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
				List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
				Optional<RuleSets> selectedRuleSets = ruleSetsList.stream().filter(ruleSets -> Objects.equals(ruleSets.getId(), id)).findFirst();
				return selectedRuleSets.get();
			}
		}


    }

	/**
	 * 
	 * <p>Title: queryStartRuleSets</p>
	 * <p>Description: 查询启动的规则集</p>
	 * @return
	 * @see com.bfd.monitor.service.RuleSetsService#queryStartRuleSets()
	 
	 */
	@Override
	public List<RuleSets> queryStartRuleSets() {
		List<RuleSets> ruleSetsList = new ArrayList<>();
		if (Constants.CONFIG_PRIORITY == 1) {
			//从内存中加载配置
			Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
			//经过Gson解析成为map的数据，经常需要通过map.get(key)获取类型为Object的值
			//List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
			List<LinkedTreeMap> ruleSetsMapList = (List<LinkedTreeMap>) jsonMap.get("rule_sets");
			for (LinkedTreeMap linkedTreeMap : ruleSetsMapList) {
				//Map转Object
				RuleSets bean = GsonUtil.mapToBean(linkedTreeMap, new RuleSets());
					//启动时需要查询status = 0 的 规则集
					if (bean.getStatus() == 0){
						ruleSetsList.add(bean);
					}
			}
		}else{

			try {
				ruleSetsList=ruleSetsMapper.queryStartRuleSets();
			} catch (Exception e) {
				LogUtil.getLogger(LogType.Beh).info("数据库此时无法使用，将从配置文件中加载数据，时间"+DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));

				//从内存中加载配置
				Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
				//经过Gson解析成为map的数据，经常需要通过map.get(key)获取类型为Object的值
				//List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
				List<LinkedTreeMap> ruleSetsMapList = (List<LinkedTreeMap>) jsonMap.get("rule_sets");
				for (LinkedTreeMap linkedTreeMap : ruleSetsMapList) {
					//Map转Object
					RuleSets bean = GsonUtil.mapToBean(linkedTreeMap, new RuleSets());
						//启动时需要查询status = 0 的 规则集
						if (bean.getStatus() == 0){
							ruleSetsList.add(bean);
						}
				}
			}
		}
		return ruleSetsList;
	}

	/**
	 * 
	 * <p>Title: distributeMonitor</p>
	 * <p>Description: 异常监控</p>
	 * @return
	 * @see com.bfd.monitor.service.RuleSetsService#distributeMonitor()
	 
	 */
    @Override
    public Map<String, String> distributeMonitor() {

        Map<String, String> map = new HashMap<>();
        String errorMessage = "";
        String time = "";
        try {
        	//查询时间
			if (Constants.CONFIG_PRIORITY == 1) {
				time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			}else{
				try {
					time = ruleSetsMapper.querySystemTime();
				} catch (Exception e) {
					time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				}
			}

        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
        if(StringUtils.isNotBlank(time)) {
            map.put("time", time);
        }else {
            map.put("errorMessage", errorMessage);
        }
        return map;
    }
}