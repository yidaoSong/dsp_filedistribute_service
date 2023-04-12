package com.bfd.monitor.service.impl;

import java.util.*;

import com.bfd.monitor.utils.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleLine;
import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.mapper.RuleMapper;
import com.bfd.monitor.mapper.RuleSetsMapper;
import com.bfd.monitor.service.RuleService;
import com.github.pagehelper.StringUtil;

/**
 *  * @ClassName:     RuleServiceImpl
 *  * @Description:规则服务实现类
 *  * @author:    Android_Robot
 *  * @date:        2018年5月23日 上午11:11:34
 *  *
 *  *
 *  
 */
@Service("RuleService")
@SuppressWarnings("all")
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private RuleSetsMapper ruleSetsMapper;


    /**
     *  * <p>Title: queryRuleSetsById</p>
     *  * <p>Description: 根据主键查询规则集信息</p>
     *  * @param id
     *  * @return
     *  * @see com.bfd.monitor.service.RuleService#queryRuleSetsById(int)
     *  
     */
	@Override
	public RuleSets queryRuleSetsById(int id) {

		//根据主键查询规则集
		RuleSets ruleSet = new RuleSets();

		if (Constants.CONFIG_PRIORITY == 1) {
			//从内存中加载配置
			Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
//			List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
//			Optional<RuleSets> selectedRuleSets = ruleSetsList.stream().filter(ruleSets -> Objects.equals(ruleSets.getId(), id)).findFirst();
//			ruleSet = selectedRuleSets.get();
            List<LinkedTreeMap> ruleSetMapList=(List<LinkedTreeMap>) jsonMap.get("rule_sets");
           List<RuleSets> linkedHashMap = new ArrayList<>();
            for (LinkedTreeMap linkedTreeMap : ruleSetMapList) {
               ruleSet = GsonUtil.mapToBean(linkedTreeMap, new RuleSets());
                if (ruleSet.getId()== id){
                   linkedHashMap.add(ruleSet);
                }
           }
        } else {
            try {
                ruleSet = ruleSetsMapper.queryRuleSetsById(id);
            } catch (Exception e) {
                LogUtil.getLogger(LogType.Beh).info("数据库此时无法使用，将从配置文件中加载数据，时间"+DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));

                //在加载优先级配置时如果是2，数据库不能使用，那么会加载静态配置文件，前提静态配置文件要提前生成好
                //从内存中加载配置
                Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
                List<LinkedTreeMap> ruleSetMapList=(List<LinkedTreeMap>) jsonMap.get("rule_sets");
                List<RuleSets> linkedHashMap = new ArrayList<>();
                for (LinkedTreeMap linkedTreeMap : ruleSetMapList) {
                    ruleSet = GsonUtil.mapToBean(linkedTreeMap, new RuleSets());
                    if (ruleSet.getId()== id){
                        linkedHashMap.add(ruleSet);
                    }
                }
            }
        }
		if (ruleSet != null && "1".equals(ruleSet.getSetType())) {
			//源目录
			String rPath = FileUtils.getRecivedPath(ruleSet.getRootCatalog(), ruleSet.getCatalog());
			//name目录
			String cataName = FileUtils.getRecivedPath(null, ruleSet.getCataName());
			//text目录
			String cataText = FileUtils.getRecivedPath(null, ruleSet.getCataText());
			String namePath = rPath + cataName;
			String textPath = rPath + cataText;

			//默认失败目录

			String failedPath = rPath + Constants.DISTRIBUTE_FOLDER_FAILED;

			ruleSet.setNamePath(namePath);

			ruleSet.setTextPath(textPath);

			ruleSet.setFailedPath(failedPath);
		}

		return ruleSet;

	}

    /**
     *  * <p>Title: updateRuleSwitch</p>
     *  * <p>Description: 修改规则状态</p>
     *  * @param switchOf
     *  * @param id
     *  * @see com.bfd.monitor.service.RuleService#updateRuleSwitch(int, int)
     *  
     */
    @Override
    public void updateRuleSwitch(int switchOf, int id) {
        ruleMapper.updateRuleSwitch(switchOf, id);
    }

    /**
     *  * <p>Title: queryRLByName</p>
     *  * <p>Description: 根据规则名称查询规则对应的线路</p>
     *  * @param ruleName
     *  * @return
     *  * @see com.bfd.monitor.service.RuleService#queryRLByName(java.lang.String)
     *  
     */
    @Override
    public Map<String, List<RuleLine>> queryRLByName(String ruleName) {
        Map<String, List<RuleLine>> map = new HashMap<>();
        //包含条件
        List<RuleLine> include = ruleMapper.queryRLByRuleName(ruleName, "0");
        //不包含条件
        List<RuleLine> exclude = ruleMapper.queryRLByRuleName(ruleName, "1");
        setCategory(include);
        setCategory(exclude);
        map.put("include", include);
        map.put("exclude", exclude);
        return map;
    }

    /**
     * 数组赋值
     *
     * @param ls
     */
    private void setCategory(List<RuleLine> ls) {
        //级联编码
        String columnCategory = "";
        //编码
        String columnCode = "";
        //cid
        String columnCid = "";

        for (int i = 0; i < ls.size(); i++) {
            //级联名称赋值
            ls.get(i).setLineCategoryName(getCategoryName(ls.get(i).getLineName()));
            columnCategory = ls.get(i).getColumnCategory();
            columnCode = ls.get(i).getColumnCodes();
            columnCid = ls.get(i).getColumnCids();
            if (columnCategory != null && !"".equals(columnCategory)) {
                //获取级联编码数组
                String[] cascadeCol = columnCategory.split(";");
                String[] codeCol = null;
                String[] cidCol = null;
                //编码数组赋值
                if (StringUtils.isNotBlank(columnCode)) {
                    codeCol = columnCode.split(",");
                }
                //cid数组赋值
                if (StringUtils.isNotBlank(columnCid)) {
                    cidCol = columnCid.split(",");
                }
                ls.get(i).setCategory(cascadeCol);
                ls.get(i).setCateCode(codeCol);
                ls.get(i).setCateCid(cidCol);

                if (cascadeCol != null && cascadeCol.length > 0) {
                    List<Map<String, String>> colList = new ArrayList<Map<String, String>>();
                    //循环数组获取栏目相关信息
                    for (int n = 0; n < cascadeCol.length; n++) {
                        String cascade = cascadeCol[n];
                        Map<String, String> colmun = new HashMap<String, String>();
                        colmun.put("cascadeName", cascade);
                        colmun.put("categoryName", getCategoryName(cascade));
                        if (codeCol != null && codeCol.length >= cascadeCol.length) {
                            for (int m = n; m < codeCol.length; m++) {
                                if (StringUtils.isNotBlank(codeCol[m])) {
                                    colmun.put("categoryCode", codeCol[m]);
                                    break;
                                }
                            }
                        }
                        if (cidCol != null && cidCol.length >= cascadeCol.length) {
                            for (int m = n; m < cidCol.length; m++) {
                                if (StringUtils.isNotBlank(cidCol[m])) {
                                    colmun.put("categoryCid", cidCol[m]);
                                    break;
                                }
                            }
                        }
                        colList.add(colmun);
                    }
                    ls.get(i).setColumnlist(colList);
                }
            }
        }
    }

    /**
     *  * @Title: getCategoryName
     *  * @Description: 根据名称获取处理名称
     *  * @param: @param cascadeName
     *  * @param: @return   
     *  * @return: String   
     *  * @throws
     *  
     */
    private String getCategoryName(String cascadeName) {
        if (StringUtil.isEmpty(cascadeName)) {
            return "";
        }
        int last = cascadeName.lastIndexOf("~");
        //级联编码
        String categoryName = "";
        if (last > 0 && cascadeName.length() > last) {
            last = last + 1;
            //截取
            categoryName = cascadeName.substring(last);
        } else {
            //直接取值
            categoryName = cascadeName;
        }
        return categoryName;
    }

    /**
     *
      * <p>Title: insertDistributeLog</p>
      * <p>Description: 分发日志插入</p>
      * @param manuscript
      * @return
      * @see com.bfd.monitor.service.RuleService#insertDistributeLog(com.bfd.monitor.bean.Manuscript)
      
     */
//	@Override
//	public int insertDistributeLog(Manuscript manuscript) {
//		return ruleMapper.insertDistributeLog(manuscript);
//	}

    /**
     *  * <p>Title: errorLog</p>
     *  * <p>Description: 分发错误处理</p>
     *  * @param ruleSet
     *  * @param manuscript
     *  * @see com.bfd.monitor.service.RuleService#errorLog(com.bfd.monitor.bean.RuleSets, com.bfd.monitor.bean.Manuscript)
     *  
     */
    @Override
    public void errorLog(RuleSets ruleSet, Manuscript manuscript) {
        //查询分发错误表中存在次数
        int errorCount = ruleMapper.queryErrorCount(ruleSet.getId(), manuscript.getFilename(), "0");
        if (errorCount > Constants.DISTRIBUTE_META_ERROR_NUM) {//调用
            ErrorDualUtils.dualErrorF(manuscript.getFilename(), ruleSet, manuscript, null, null, 2);
            //稿件规则集表插入错误日志记录
            manuscript.setDistributeResult(1);
            manuscript.setDistributeResultMsg("TEXT目录" + ruleSet.getTextPath() + "中文件" + manuscript.getFilename() + "附件不全。");
            try {
                insertLog(0, manuscript);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {//插入一条数据
            //错误次数不超过3次，错误表中插入一条数据
            ruleMapper.addError(manuscript.getFilename(), "0", ruleSet.getId());
        }
    }

    /**
     *  * <p>Title: queryRuleBySet</p>
     *  * <p>Description: 根据条件查询规则信息</p>
     *  * @param rule
     *  * @return
     *  * @see com.bfd.monitor.service.RuleService#queryRuleBySet(com.bfd.monitor.bean.Rule)
     *  
     */
    @Override
    public List<Rule> queryRuleBySet(Rule rule) {
        List<Rule> ruleList =new ArrayList<>();
        if (Constants.CONFIG_PRIORITY ==1 ){
            //从内存中加载配置
            Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
            //经过Gson解析成为map的数据，经常需要通过map.get(key)获取类型为Object的值
            //List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
            List<LinkedTreeMap> ruleMapList = (List<LinkedTreeMap>) jsonMap.get("rule");
            for (LinkedTreeMap linkedTreeMap : ruleMapList) {
                //rule1 为静态配置文件bean
                Rule rule1 = GsonUtil.mapToBean(linkedTreeMap, new Rule());
                try {
                    if (rule.getSetid() ==rule1.getSetid() && rule1.getSetStatus()==0 && rule.getSendMode() ==rule1.getSendMode() && rule.getReceiveMode()==rule1.getReceiveMode() ){
                        ruleList.add(rule1);
                    }
                } catch (Exception e) {
                    LogUtil.getLogger(LogType.Run).error("没有与此规则匹配的规则集信息,请查看:规则集id,启动状态,发送方式,接收方式等参数信息");
                }
            }
        }else{
            ruleList =ruleMapper.queryRuleBySet(rule);
        }
//传过来的是rule  用来跟数据库比对的，也就是现在比对的是静态文件的
        return ruleList;
    }

    /**
     *  * <p>Title: getRuleLine</p>
     *  * <p>Description: 查询线路信息</p>
     *  * @param rule
     *  * @return
     *  * @see com.bfd.monitor.service.RuleService#getRuleLine(com.bfd.monitor.bean.Rule)
     *  
     */
    @Override
    public Rule getRuleLine(Rule rule) {
        List<RuleLine> ruleLineList = new ArrayList<>();
        List<RuleLine> include;
        List<RuleLine> exclude;
        if (rule != null) {
            if (Constants.CONFIG_PRIORITY == 1) {
                Map map1 = JsonFileConfigUtil.getJsonConfigMap();
                List<LinkedTreeMap> ruleLineMapList = (List<LinkedTreeMap>) map1.get("rule_line");
                for (LinkedTreeMap linkedTreeMap : ruleLineMapList) {

                    RuleLine ruleLine = GsonUtil.mapToBean(linkedTreeMap, new RuleLine());//遍历的数据转化成bean

                    // 包含条件中的线路
                    if (ruleLine.getRuleId() == rule.getId() && ruleLine.getRelation() == 0) {
                        ruleLineList.add(ruleLine);
                        setCategory(ruleLineList);
                        rule.setIncludeLC(ruleLineList);

                    } else if (ruleLine.getRuleId() == rule.getId() && ruleLine.getRelation() == 1) {

                        ruleLineList.add(ruleLine);
                        setCategory(ruleLineList);
                        rule.setExceptLC(ruleLineList);
                    }
                }
            }
            else {
                try {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ruleId", rule.getId());
                    map.put("relation", "0");
                    //包含条件中的线路
                    include = ruleMapper.queryRLByRuleId(map);
                    map.put("relation", "1");
                    //不包含条件中的线路
                    exclude = ruleMapper.queryRLByRuleId(map);
                    setCategory(include);
                    setCategory(exclude);
                    rule.setIncludeLC(include);
                    rule.setExceptLC(exclude);
                } catch (Exception e) {
                    LogUtil.getLogger(LogType.Beh).info("数据库此时无法使用，将从配置文件中加载数据，时间"+DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));

                    //在加载优先级配置时如果是2，数据库不能使用，那么会加载静态配置文件，前提静态配置文件要提前生成好
                    Map map1 = JsonFileConfigUtil.getJsonConfigMap();
                    List<LinkedTreeMap> ruleLineMapList = (List<LinkedTreeMap>) map1.get("rule_line");
                    for (LinkedTreeMap linkedTreeMap : ruleLineMapList) {

                        RuleLine ruleLine = GsonUtil.mapToBean(linkedTreeMap, new RuleLine());//遍历的数据转化成bean

                        // 包含条件中的线路
                        if (ruleLine.getRuleId() == rule.getId() && ruleLine.getRelation() == 0) {
                            ruleLineList.add(ruleLine);
                            setCategory(ruleLineList);
                            rule.setIncludeLC(ruleLineList);

                        } else if (ruleLine.getRuleId() == rule.getId() && ruleLine.getRelation() == 1) {

                            ruleLineList.add(ruleLine);
                            setCategory(ruleLineList);
                            rule.setExceptLC(ruleLineList);
                        }
                    }
                }
            }
        }

        return rule;
    }

    /**
     *  * <p>Title: queryRuleBySetNoStatus</p>
     *  * <p>Description: 根据条件查询规则信息</p>
     *  * @param ruleDemo
     *  * @return
     *  * @see com.bfd.monitor.service.RuleService#queryRuleBySetNoStatus(com.bfd.monitor.bean.Rule)
     *  
     */
    @Override
    public List<Rule> queryRuleBySetNoStatus(Rule ruleDemo) {
        return ruleMapper.queryRuleBySetNoStatus(ruleDemo);
    }

    /**
     * 根据规则集主键查询所有文件到文件的规则
     */

    @Override
    public List<Rule> queryRuleAllBySet(Rule ruleDemo) {
        List<Rule> ruleList = new ArrayList<>();
        if (Constants.CONFIG_PRIORITY == 1) {
            //从内存中加载配置
            Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
            //经过Gson解析成为map的数据，经常需要通过map.get(key)获取类型为Object的值
            //List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
            List<LinkedTreeMap> ruleMapList = (List<LinkedTreeMap>) jsonMap.get("rule");
            for (LinkedTreeMap linkedTreeMap : ruleMapList) {
                //rule1 为静态配置文件bean
                Rule rule1 = GsonUtil.mapToBean(linkedTreeMap, new Rule());

                if (ruleDemo.getSetid() == rule1.getSetid() && ruleDemo.getSendMode() == rule1.getSendMode() && ruleDemo.getReceiveMode() == rule1.getReceiveMode()) {
                    ruleList.add(rule1);
                }
            }
        } else {
            try {
                ruleList = ruleMapper.queryRuleAllBySet(ruleDemo);
            } catch (Exception e) {
                LogUtil.getLogger(LogType.Beh).info("数据库此时无法使用，将从配置文件中加载数据，时间"+DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));

                //在加载优先级配置时如果是2，数据库不能使用，那么会加载静态配置文件，前提静态配置文件要提前生成好
                //从内存中加载配置
                Map jsonMap = JsonFileConfigUtil.getJsonConfigMap();
                //经过Gson解析成为map的数据，经常需要通过map.get(key)获取类型为Object的值
                //List<RuleSets> ruleSetsList = (List<RuleSets>) jsonMap.get("rule_sets");
                List<LinkedTreeMap> ruleMapList = (List<LinkedTreeMap>) jsonMap.get("rule");
                for (LinkedTreeMap linkedTreeMap : ruleMapList) {
                    //rule1 为静态配置文件bean
                    Rule rule1 = GsonUtil.mapToBean(linkedTreeMap, new Rule());

                    if (ruleDemo.getSetid() == rule1.getSetid() && ruleDemo.getSendMode() == rule1.getSendMode() && ruleDemo.getReceiveMode() == rule1.getReceiveMode()) {
                        ruleList.add(rule1);
                    }
                }
            }
        }
//传过来的是rule  用来跟数据库比对的，也就是现在比对的是静态文件的
        return ruleList;
    }

    @Override
    public void cleanLogHalfYear(String time) {

        ruleMapper.cleanDistributeLog(time);
        ruleMapper.cleanDistributeRulesetLog(time);
        ruleMapper.cleanRuleMonitorException(time);
    }

    /**
     *
      * <p>Title: insertDistributeRuleSetLog</p>
      * <p>Description: </p>
      * @param manuscript
      * @return
      * @see com.bfd.monitor.service.RuleService#insertDistributeRuleSetLog(com.bfd.monitor.bean.Manuscript)
      
     */
//	@Override
//	public int insertDistributeRuleSetLog(Manuscript manuscript) {
//		return ruleMapper.insertDistributeRuleSetLog(manuscript);
//	}

    /**
     *  * @Title: insertLog
     *  * @Description: 插入日志
     *  * @param: @param flag
     *  * @param: @param manuscript
     *  * @param: @return   
     *  * @return: int   
     *  * @throws
     *  
     */
    @Override
    public int insertLog(int flag, Manuscript manuscript) throws Exception  {
        if (flag == 0) {
            return ruleMapper.insertDistributeRuleSetLog(manuscript);
        } else {
            return ruleMapper.insertDistributeLog(manuscript);
        }
    }
}