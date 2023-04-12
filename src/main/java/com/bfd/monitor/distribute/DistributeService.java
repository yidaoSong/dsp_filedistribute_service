package com.bfd.monitor.distribute;

import com.bfd.monitor.bean.*;
import com.bfd.monitor.service.RuleService;
import com.bfd.monitor.utils.*;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 *  * @ClassName:     DistributeService
 *  * @Description:分发服务
 *  * @author:    Android_Robot
 *  * @date:        2018年5月23日 上午10:57:04
 *  *
 *  *
 *  
 */
public class DistributeService {
    //规则服务接口
    private static RuleService ruleService = (RuleService) SpringContextUtil.getBean("RuleService");

    /**
     *  * @Title: distribute
     *  * @Description: 稿件分发
     *  * @param: @param jobNameId
     *  * @param: @param ruleSet
     *  * @param: @param rulelistFtoF   
     *  * @return: void   
     *  * @throws
     *  
     */
    public static void distribute(String jobNameId, RuleSets ruleSet, List<Rule> rulelistFtoF) {
        if (StringUtils.isBlank(jobNameId)) {
            LogUtil.getLogger(LogType.Run).error("--【规则分发】---规则线程信息为空，请及时查看。");
            return;
        }
        //获取任务名称
        jobNameId = jobNameId.replace("rule_", "").trim();
//		RuleSets ruleSet = ruleService.queryRuleSetsById(Integer.parseInt(jobNameId));
        if (ruleSet == null) {
            LogUtil.getLogger(LogType.Run).error("--【" + jobNameId + "分发】---规则集信息为空，请及时查看。");
            return;
        }
        LogUtil.getLogger(LogType.Beh).info("规则集【" + ruleSet.getSetName() + "(" + ruleSet.getId() + ")】--------在" + DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "开始轮询。");
        //获取规则制定目录或者制定队列中的所有稿件，并过滤出需要分发的稿件
        List<Manuscript> manuscriptlist = getManuscriptByRule(ruleSet);
        //获取并校验所有的规则详情
        if ("1".equals(ruleSet.getSetType())) {
            //稿件分发开始分发的操作
            distributeManuscriptByRuleFtoF(ruleSet, manuscriptlist, rulelistFtoF);
        } else {
            //规则集来源为消息队列，待开发...
            //下发为文件和消息的情况都做判断
        }
        LogUtil.getLogger(LogType.Beh).info("规则集【" + ruleSet.getSetName() + "(" + ruleSet.getId() + ")】--------在" + DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "结束分发。");
    }

    /**
     * 分发稿件
     * @param distributeList
     * @param rule
     */
//	public static void distributeManuscriptByRule(List<Manuscript> distributeList,Rule rule){
//		if(FileUtils.isBlankList(distributeList) || rule==null){
//			return ;
//		}
//		for (Manuscript manuscript : distributeList) {
//			if(rule.getSendMode() == 0){
//				System.out.println("稿件下发是消息的，待开发.......");
//			}else if(StringUtils.isNotBlank(rule.getSendPath()) && rule.getSendMode() == 1 && rule.getReceiveMode() == 1){
//				distributeFtoF(manuscript, rule);
//			}else if(StringUtils.isNotBlank(rule.getSendPath()) && rule.getSendMode() == 1 && rule.getReceiveMode() == 0){
//				System.out.println("稿件按照消息-文件分发的，待开发.......");
//			}
//		}
//	}

    /**
     *  * @Title: distributeManuscriptByRuleFtoF
     *  * @Description: 目录-目录分发稿件
     * 根据过滤后的文件（稿件内容和附件均完整）和过滤后的规则信息（需要分发的规则的下发目录是否有操作权限）轮询判断分发
     *  * @param: @param ruleSet
     *  * @param: @param manuscriptlist
     *  * @param: @param rulelistFtoF   
     *  * @return: void   
     *  * @throws
     *  
     */
    public static void distributeManuscriptByRuleFtoF(RuleSets ruleSet, List<Manuscript> manuscriptlist, List<Rule> rulelistFtoF) {
        if (FileUtils.isBlankList(manuscriptlist) && FileUtils.isBlankList(rulelistFtoF) && ruleSet == null) {
            return;
        }
        //true false false
        //循环稿件
        for (Manuscript manuscript : manuscriptlist) {
            manuscript.setDistributeResult(0);//稿件分发默认成功
            manuscript.setDistributeResultMsg("");//稿件分发结果默认赋值
            LogUtil.getLogger(LogType.Ope).info("稿件" + manuscript.getFilename() + " 在" + DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "开始分发");
            //验证稿件是否可分发
            List<Rule> filterRuleList = checkManuscriptByRule(rulelistFtoF, manuscript);
            LogUtil.getLogger(LogType.Ope).info(">>>可分发稿件数量{}<<<",filterRuleList.size());

            for (Rule rule : filterRuleList) {
                //一篇稿件同时分发多个规则，分发过程中出错的稿件信息复制到failed目录（name目录中未删除）中
                //同时删除对应规则的waiting目录中已经分发的附件文件信息
                distributeFtoF(manuscript, rule, ruleSet);

                if (manuscript.getDistributeStatus() != 0) {
                    manuscript.setDistributeResult(1);
                }
                manuscript.setDistributeResultMsg(manuscript.getDistributeResultMsg() + manuscript.getDistributeMsg() + "；");
            }
            //本篇稿件按规则需要分发
            if (filterRuleList.size() > 0) {
                try {
                    ruleService.insertLog(0, manuscript);
                } catch (Exception e) {
                    LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}", manuscript, e);
                }
            } else {
                LogUtil.getLogger(LogType.Ope).info("稿件" + manuscript.getFilename() + " 在规则集：" + ruleSet.getSetName() + "(" + ruleSet.getId() + ")中无符合条件的分发规则，未分发。");
            }
            //一篇稿件等所有规则分发完（无论是否成功分发），删除分发name目录下的稿件及附件信息
            MoveFileUtil.deleteManuscriptFileFromName(manuscript, ruleSet);
        }
    }

    /**
     *  * @Title: distributeFtoF
     *  * @Description: 分发稿件（文件-文件）
     *  * @param: @param manuscript
     *  * @param: @param rule
     *  * @param: @param ruleSet   
     *  * @return: void   
     *  * @throws
     *  
     */
    public static void distributeFtoF(Manuscript manuscript, Rule rule, RuleSets ruleSet) {
        LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---稿件" + manuscript.getFilename() + "开始准备分发到" + rule.getWaitingPath() + "。");
        manuscript.setRuleId(rule.getId());
        manuscript.setDistributeType(0);
        List<String> metaPaths = manuscript.getMetaPaths();
        List<Map<String, String>> metaMsg = new ArrayList<Map<String, String>>();//用于记录附件和稿件的落盘时间
        String errorMes = "";
        if (!FileUtils.isBlankList(metaPaths)) {
            for (String string : metaPaths) {
                if (!distributeFileFtoF(string, rule, ruleSet)) {//首先分发稿件的附件到下发目录
                    LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---稿件附件" + string + "分发到WAITING失败。");
//					manuscript.setDistributeMsg("稿件"+manuscript.getFilename()+"的附件"+string+"由"+ruleSet.getNamePath()+"分发"+ruleSet.getTextPath()+"的地址到"+rule.getWaitingPath()+"失败，现已保存到"+ruleSet.getFailedPath());
//					ruleService.insertDistributeLog(manuscript);
                    //稿件分发错误处理方法
                    ErrorDualUtils.dualDistributeErrorF(manuscript.getFilename(), ruleSet, manuscript, rule.getWaitingPath(), rule, 8);
                    manuscript.setDistributeStatus(8);

                    //错误消息拼接
                    errorMes = "稿件附件：" + string + "由目录：" + ruleSet.getRootCatalog() + ruleSet.getCatalog() + ruleSet.getCataName() + "分发到目录：";
                    if (StringUtils.isNotBlank(rule.getWaitingPath())) {
                        errorMes += rule.getWaitingPath();
                    }

                    errorMes += "失败";
                    manuscript.setDistributeMsg(errorMes);

//					ruleService.insertLog(1,manuscript);
                    try {
                        ruleService.insertLog(1, manuscript);
                    } catch (Exception e) {
                        LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}", manuscript, e);
                    }
                    return;
                } else {
                    Map<String, String> msg = new HashMap<String, String>();
                    msg.put("name", string);
                    msg.put("time", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    metaMsg.add(msg);
//					String namePath = ruleSet.getNamePath()+string;
//					MoveFileUtil.deleteFile(namePath);

//					manuscript.setDistributeStatus(0);
//					manuscript.setDistributeMsg("稿件"+manuscript.getFilename()+"的附件"+string+"由"+ruleSet.getNamePath()+"分发"+ruleSet.getTextPath()+"的地址到"+rule.getWaitingPath()+"成功");
//					ruleService.insertDistributeLog(manuscript);
                    LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---稿件" + manuscript.getFilename() + "的附件" + string + "分发到" + rule.getWaitingPath() + "目录成功。");
                }
            }
        }
        if (!distributeFileFtoF(manuscript.getFilename(), rule, ruleSet)) {
            LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---稿件" + manuscript.getFilename() + "分发失败。");
//		    manuscript.setDistributeMsg("稿件"+manuscript.getFilename()+"由"+ruleSet.getNamePath()+"分发"+ruleSet.getTextPath()+"的地址到"+rule.getWaitingPath()+"失败，现已保存到"+ruleSet.getFailedPath());
//		    ruleService.insertDistributeLog(manuscript);
            //稿件分发错误处理方法
            ErrorDualUtils.dualDistributeErrorF(manuscript.getFilename(), ruleSet, manuscript, rule.getWaitingPath(), rule, 9);
            //错误消息拼接
            errorMes = "稿件：" + manuscript.getFilename() + "由目录：" + ruleSet.getRootCatalog() + ruleSet.getCatalog() + ruleSet.getCataName() + "分发到目录：";
            if (StringUtils.isNotBlank(rule.getWaitingPath())) {
                errorMes += rule.getWaitingPath();
            }

            errorMes += "失败";

            manuscript.setDistributeMsg(errorMes);

//			ruleService.insertLog(1,manuscript);
            try {
                ruleService.insertLog(1, manuscript);
            } catch (Exception e) {
                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}", manuscript, e);
            }
        } else {
//		    String namePath = ruleSet.getNamePath()+manuscript.getFilename();
//		    MoveFileUtil.deleteFile(namePath);
            if (!FileUtils.isBlankList(metaMsg)) {
                for (Map<String, String> map : metaMsg) {
                    String name = map.get("name");
                    String time = map.get("time");
                    //记录稿件处理日志
                    LogMesUtil.getDistributeMsg(ruleSet.getSetName(), rule.getRuleName(), name, time);
                }
            }
            manuscript.setDistributeStatus(0);
            manuscript.setDistributeMsg("稿件：" + manuscript.getFilename() + "由" + ruleSet.getNamePath() + "分发" + ruleSet.getTextPath() + "的地址到" + rule.getWaitingPath() + "成功");
//		    ruleService.insertLog(1,manuscript);
            try {
                ruleService.insertLog(1, manuscript);
            } catch (Exception e) {
                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}", manuscript, e);
            }
            //记录稿件处理日志
            LogMesUtil.getDistributeMsg(ruleSet.getSetName(), rule.getRuleName(), manuscript.getFilename(), DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---稿件" + manuscript.getFilename() + "分发成功。");
        }
    }

    /**
     *  * @Title: distributeFileFtoF
     *  * @Description: 上传稿件的附件到分发目录
     *  * @param: @param metaName
     *  * @param: @param rule
     *  * @param: @param ruleSet
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean distributeFileFtoF(String metaName, Rule rule, RuleSets ruleSet) {
        boolean vali = false;
        //拼接完成目录
        String metaPath = ruleSet.getTextPath() + metaName;

        if (FileUtils.isExistsFile(metaPath)) {
            File sendMeta = new File(rule.getWaitingPath() + metaName);
            vali = MoveFileUtil.createFile(sendMeta, metaPath, "UTF-8");
        } else {
            LogUtil.getLogger(LogType.Run).error("--【rule_" + rule.getId() + "分发】---在分发TEXT目录中未找到文件" + metaName + "。");
        }
        return vali;
    }

    /**
     *  * @Title: checkManuscriptByRule
     *  * @Description: 按照规则的基本条件过滤判断稿件是否需要分发
     *  * @param: @param ruleList
     *  * @param: @param manuscript
     *  * @param: @return   
     *  * @return: List<Rule>   
     *  * @throws
     *  
     */
    public static List<Rule> checkManuscriptByRule(List<Rule> ruleList, Manuscript manuscript) {
        List<Rule> reslist = new ArrayList<>();
        if (!FileUtils.isBlankList(ruleList)) {
            for (Rule rule : ruleList) {
                //根据规则验证稿件是否分发
                if (checkManuscript(rule, manuscript)) {
                    LogUtil.getLogger(LogType.Ope).info(">>>根据规则验证稿件需要分发{}<<<",rule.getId());
                    reslist.add(rule);
                }
            }
        }
        return reslist;
    }

    /**
     *  * @Title: checkFileName
     *  * @Description: 文件名（多值用英文逗号分隔）左匹配（可以判断ALL，带英文逗号分割的）
     *  * @param: @param item
     *  * @param: @param ruleItem
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkFileName(String item, String ruleItem) {
        boolean vali = false;
        if (StringUtils.isBlank(ruleItem)) {
            return false;
        }
        //规则为ALL的判断
        if ("ALL".equals(ruleItem.trim().toUpperCase())) {
            vali = true;
        } else {
            //规则不为ALL多值判断
            String[] ruleItems = ruleItem.split(",");
            for (String string : ruleItems) {
                if (StringUtils.isNotBlank(string) && item.toUpperCase().startsWith(string.trim().toUpperCase())) {
                    vali = true;
                    break;
                }
            }
        }
        LogUtil.getLogger(LogType.Ope).info(">>>文件名验证{},文件名:{},规则:{}<<<",vali,item,ruleItem);

        return vali;
    }

    /**
     *  * @Title: checkManuscript
     *  * @Description: 依次判断包含与不包含条件
     *  * @param: @param rule
     *  * @param: @param manuscript
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkManuscript(Rule rule, Manuscript manuscript) {
        // 文件名（多值用英文逗号分隔）左匹配
        boolean fileName = checkFileName(manuscript.getFilename(), rule.getFileName());
        if (!fileName) {
            return false;
        }
        //语种验证
        boolean lan = checkDLSCbyIandE(manuscript.getLanguage(), rule.getLanguage(), rule.getLanguageExclude(), rule.getId());
        //系统编号验证
        boolean sys = checkDLSCbyIandE(manuscript.getSystemnum(), rule.getSystemnum(), rule.getSystemnumExclude(), rule.getId());
        //稿件类型验证
        boolean con = checkDLSCbyIandE(manuscript.getContenttype(), rule.getContenttype(), rule.getContenttypeExclude(), rule.getId());
        boolean dep = false;
        boolean lc = false;

        if (rule.getAttribute() == 1 && "F".equals(manuscript.getType())) {//成品稿
            //部门验证
            dep = checkDLSCbyIandE(manuscript.getDepartment(), rule.getDepartment(), rule.getDepartmentExclude(), rule.getId());
            //线路验证
            lc = checkLCbyIandE(manuscript, rule.getIncludeLC(), rule.getExceptLC(), rule.getId());
        } else if (rule.getAttribute() == 0 && "S".equals(manuscript.getType())) {//未定稿
            //部门验证
            dep = checkSfileDepbyIandE(manuscript.getDeplist(), rule.getDepartment(), rule.getDepartmentExclude(), rule.getId());
            lc = true;
        } else {
            return false;
        }

        LogUtil.getLogger(LogType.Ope).info(">>>依次判断包含与不包含条件,部门验证:{},语种验证:{},系统编号验证:{},稿件类型验证:{},线路验证:{}<<<",dep,lan,sys,con,lc);
        if (dep && lan && sys && con && lc) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  * @Title: getManuscriptByRule
     *  * @Description: 获取规则制定目录或者制定队列中的所有稿件，并过滤出需要分发的稿件
     *  * @param: @param ruleSet
     *  * @param: @return   
     *  * @return: List<Manuscript>   
     *  * @throws
     *  
     */
    public static List<Manuscript> getManuscriptByRule(RuleSets ruleSet) {
        List<Manuscript> manuList = new ArrayList<Manuscript>();
        if ("0".equals(ruleSet.getSetType())) {
            // System.out.println("稿件来源是消息的，待开发.......");
            LogUtil.getLogger(LogType.Run).info("稿件来源是消息的，待开发.......");
        } else if ("1".equals(ruleSet.getSetType())) {
            //从name目录中获取待分发的稿件

            File folder = new File(ruleSet.getNamePath());

            if (folder.exists() && folder.isDirectory()) {//文件夹存在
                File[] manuFiles = folder.listFiles();
                for (File file : manuFiles) {
                    //稿件名称
                    String fileName = file.getName();
                    if (FileUtils.isManuscript(fileName)) {//判断是否是稿件，过滤掉附件

                        //稿件信息获取  new一个Manuscript封装类
                        Manuscript manuscript = new Manuscript();

                        manuscript.setReceiveMode(1);
                        manuscript.setFilename(fileName);
                        manuscript.setSetId(ruleSet.getId());

                        manuscript = getManuscriptByFileName(fileName, manuscript);

                        //文件
                        File textfile = new File(ruleSet.getTextPath() + fileName);
                        if (textfile.exists() && textfile.isFile()) {
                            //验证稿件是否需要分发
                            manuscript = DistributeUtil.checkManuscriptInfo(manuscript, textfile, ruleSet);
                            if (manuscript != null) {
                                LogUtil.getLogger(LogType.Ope).info(LogMesUtil.getManuscriptMsg(manuscript));
                                manuList.add(manuscript);
                            }
                        } else {
                            LogUtil.getLogger(LogType.Ope).error("【" + ruleSet.getSetName() + "】的未找到稿件源文件" + fileName + "。");
                            LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---在分发的TEXT目录" + ruleSet.getTextPath() + "中未找到文件" + fileName + "。");
                            //稿件分发错误日志记录
                            ErrorDualUtils.dualErrorF(fileName, ruleSet, manuscript, null, null, 7);
                            //稿件规则集表插入错误日志记录
                            manuscript.setDistributeResult(1);
                            manuscript.setDistributeResultMsg("在分发的TEXT目录" + ruleSet.getTextPath() + "中未找到文件" + fileName + "。");
                            try {
                                ruleService.insertLog(0, manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}", manuscript, e);
                            }
                            continue;
                        }
                    } else if (!FileUtils.isNotErrorFileName(fileName)) {//非稿件非附件，命名规则与正常稿件没有一致性，则移动到failed中，规则集记录失败
                        Manuscript manuscript = new Manuscript();
                        manuscript.setReceiveMode(1);
                        manuscript.setFilename(fileName);
                        manuscript.setSetId(ruleSet.getId());
                        manuscript.setDistributeResult(1);
                        manuscript.setDistributeResultMsg("稿件CNML文件名" + fileName + "不符合规范，无法解析文件名。");
                        try {
                            ruleService.insertLog(0, manuscript);
                        } catch (Exception e) {
                            LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}", manuscript, e);
                        }
                        manuscript = null;
                        ErrorDualUtils.dualErrorF(fileName, ruleSet, manuscript, null, null, 8);
                        /*LogUtil.getLogger(LogType.Ope).error("*********稿件"+textfile.getName()+"的XML格式错误*********");*/
                        LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---稿件CNML文件名" + fileName + "不符合规范，无法解析文件名。");
                    }
                }
            }
        }
        return manuList;
    }

    /**
     *  * @Title: getManuscriptByFileName
     *  * @Description: 通过文件名获取稿件的DLSC相关信息
     *  * @param: @param fileName
     *  * @param: @param manuscript
     *  * @param: @return   
     *  * @return: Manuscript   
     *  * @throws
     *  
     */
    public static Manuscript getManuscriptByFileName(String fileName, Manuscript manuscript) {
        //稿件名称不能为空，长度小于29位
        if (StringUtils.isBlank(fileName) || fileName.length() < 29) {
            return manuscript;
        }

        String department = fileName.substring(0, 6);//发稿部门
        manuscript.setDepartment(department);
        String language = fileName.substring(6, 7);//语种
        manuscript.setLanguage(language);
        String fileName2 = fileName.substring(fileName.lastIndexOf("_"));
        String systemnum = fileName2.substring(1, 3);//系统编号
        manuscript.setSystemnum(systemnum);
        String contenttypen = fileName2.substring(3, 4);//稿件类型
        manuscript.setContenttype(contenttypen);
        String type = fileName2.substring(4, 5);//稿件种类
        manuscript.setType(type);
        // System.out.println("稿件" + fileName + "，发稿部门：" + department + "，语种：" + language + "，系统编号：" + systemnum + "，稿件类型：" + contenttypen + "，稿件种类：" + type);
        LogUtil.getLogger(LogType.Run).info("稿件" + fileName + "，发稿部门：" + department + "，语种：" + language + "，系统编号：" + systemnum + "，稿件类型：" + contenttypen + "，稿件种类：" + type);
        return manuscript;
    }

    /**
     *  * @Title: checkDistributeLC
     *  * @Description: 判断线路与栏目是否符合
     *  * @param: @param manuscript
     *  * @param: @param ruleList
     *  * @param: @param ruleId
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkDistributeLC(Manuscript manuscript, List<RuleLine> ruleList, int ruleId) {
        boolean vali = false;
        //线路类型
        boolean lineFlag = false;
        //栏目类型
        boolean colFlag = false;

        //稿件及稿件中线路不能为空
        if (manuscript == null || FileUtils.isBlankList(manuscript.getLinelist())) {
            return false;
        }
        for (LineColumn line : manuscript.getLinelist()) {
            for (RuleLine lineRule : ruleList) {
                //线路编码
                String lcode = lineRule.getLineCode().trim().toUpperCase();
                //修改
                boolean typeFlag = FileUtils.isTypeOk(manuscript.getType());

                if (lcode.equals(line.getLineId().trim().toUpperCase()) && typeFlag) {
                    LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleId + "分发】---稿件" + manuscript.getFilename() + "的线路" + lcode + "符合条件。");
                    String ccode = lineRule.getColumnCodes();
                    List<String> columnlist = line.getColumnId();
                    if ((!FileUtils.isBlankList(columnlist)) && StringUtils.isNotBlank(ccode)) {
                        if (!ccode.endsWith(",")) {
                            ccode = ccode + ",";
                        }
                        //栏目判断
                        for (String column : columnlist) {
                            if (ccode.contains(column.trim() + ",")) {
                                LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleId + "分发】---稿件" + manuscript.getFilename() + "的栏目" + column + "符合条件" + ccode + "。");
                                colFlag = true;
                                break;
                            }
                        }
                    } else if (StringUtils.isBlank(ccode)) {
                        LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleId + "分发】---规则没有栏目限制稿件" + manuscript.getFilename() + "的栏目无需判断。");
                        colFlag = true;
                    }
                    lineFlag = true;
                    break;
                }
            }
            if (lineFlag && colFlag) {
                vali = true;
                break;
            }
        }
        LogUtil.getLogger(LogType.Ope).info(">>>判断线路与栏目是否符合{},manuscript:{},ruleList:{},ruleId:{}<<<",vali,manuscript,ruleList,ruleId);
        return vali;
    }

    /**
     *  * @Title: checkLCbyIandE
     *  * @Description: 判断给定的条件中是否包含稿件的一个线路与对应名称
     *  * @param: @param manuscript
     *  * @param: @param includeList
     *  * @param: @param exceptList
     *  * @param: @param ruleId
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkLCbyIandE(Manuscript manuscript, List<RuleLine> includeList, List<RuleLine> exceptList, int ruleId) {
        List<RuleLine> ruleList = includeList;
        boolean ruleFlag = false;
        if (FileUtils.isBlankList(includeList) && (!FileUtils.isBlankList(exceptList))) {
            ruleList = exceptList;
            ruleFlag = false;
        } else if (FileUtils.isBlankList(exceptList) && (!FileUtils.isBlankList(includeList))) {
            ruleList = includeList;
            ruleFlag = true;
        } else {
            LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleId + "分发】---分发的规则线路与栏目信息不全，稿件不考虑线路栏目信息。");
            return true;
        }

        boolean vali = false;
        if (ruleFlag) {//包含条件中的线路判断
            if (ruleIsAll(ruleList)) {//判断ALL的功能
                vali = true;
            } else if (checkDistributeLC(manuscript, ruleList, ruleId)) {//校验稿件是否可分发
                vali = true;
            }
        } else {
            if (!checkDistributeLC(manuscript, ruleList, ruleId)) {//校验稿件是否可分发
                vali = true;
            }
        }
        LogUtil.getLogger(LogType.Ope).info(">>>判断给定的条件中是否包含稿件的一个线路与对应名称{},manuscript:{},includeList:{},exceptList:{},ruleId:{}<<<",vali,manuscript,includeList,exceptList,ruleId);
        return vali;
    }

    /**
     *  * @Title: ruleIsAll
     *  * @Description: 验证是否为ALL
     *  * @param: @param rulelist
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean ruleIsAll(List<RuleLine> rulelist) {
        boolean vali = false;
        if (rulelist != null && rulelist.size() > 0) {
            for (RuleLine ruleLine : rulelist) {
                //ruleLine!=null && StringUtils.isNotBlank(ruleLine.getLineCode())&&
                if ("ALL".equals(ruleLine.getLineCode().trim().toUpperCase())) {
                    vali = true;
                    break;
                }
            }
        }
        return vali;
    }

    /**
     *  * @Title: checkMetaExist
     *  * @Description: 判断稿件附件是否都存在
     *  * @param: @param manuscript
     *  * @param: @param rule
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkMetaExist(Manuscript manuscript, Rule rule) {
        boolean vali = false;
        if (FileUtils.isBlankList(manuscript.getMetaPaths())) {//没有附件
            vali = true;
        } else {
            boolean errorvali = false;
            List<String> metalist = manuscript.getMetaPaths();
            for (String string : metalist) {
                String metaPath = rule.getTextPath() + string;
                if (!FileUtils.isExistsFile(metaPath)) {
                    LogUtil.getLogger(LogType.Run).error("--【rule_" + rule.getId() + "分发】---在分发TEXT目录中未找到文件" + string + "。");
                    errorvali = true;
                    break;
                }
            }
            if (!errorvali) {
                vali = true;
            }
        }
        return vali;
    }

    /**
     *  * @Title: checkMetaComplete
     *  * @Description: 判断稿件的附件是否完整
     *  * @param: @param manuscript
     *  * @param: @param ruleSet
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkMetaComplete(Manuscript manuscript, RuleSets ruleSet) {
        boolean vali = false;
        if (FileUtils.isBlankList(manuscript.getMetaPaths())) {//没有附件
            vali = true;
        } else {
            boolean errorvali = false;
            List<String> metalist = manuscript.getMetaPaths();
            for (String string : metalist) {
                String metaPath = ruleSet.getTextPath() + string;
                if (!FileUtils.isExistsFile(metaPath)) {
                    LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---在分发TEXT目录" + ruleSet.getTextPath() + "中未找到文件" + string + "。");
                    errorvali = true;
                    break;
                }
            }
            if (!errorvali) {
                vali = true;
            }
        }
        return vali;
    }

    /**
     * 通过DLSC判断是否需要分发(包含)
     * @param manuscript 稿件
     * @param rule 规则
     * @return
     */
	/*public static boolean isDistributeByDLSC(Manuscript manuscript,Rule rule){
		boolean vali = false;
		if(checkDistributeItem(manuscript.getDepartment(), rule.getDepartment())
				&&checkDistributeItem(manuscript.getContenttype(), rule.getContenttype())
				&&checkDistributeItem(manuscript.getSystemnum(), rule.getSystemnum())
				&&checkDistributeItem(manuscript.getLanguage(), rule.getLanguage())){
			vali = true;
		}
		return vali;
	}*/

    /**
     * 通过DLSC判断是否需要分发(不包含)
     * @param manuscript
     * @param rule
     * @return
     */
	/*public static boolean isNotDistributeByDLSC(Manuscript manuscript,Rule rule){
		boolean vali = false;
		if(checkDistributeItem(manuscript.getDepartment(), rule.getDepartmentExclude())
				&&checkDistributeItem(manuscript.getContenttype(), rule.getContenttypeExclude())
				&&checkDistributeItem(manuscript.getSystemnum(), rule.getSystemnumExclude())
				&&checkDistributeItem(manuscript.getLanguage(), rule.getLanguageExclude())){
			vali = true;
		}
		return vali;
	}*/

    /**
     *  * @Title: checkDistributeItem
     *  * @Description: 输入字段的判断（可以判断ALL，带英文逗号分割的）
     *  * @param: @param item
     *  * @param: @param ruleItem
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkDistributeItem(String item, String ruleItem) {
        boolean vali = false;
        if (StringUtils.isEmpty(ruleItem)) {
            return false;
        }
        if ("ALL".equals(ruleItem)) {
            vali = true;
        } else {
            String[] ruleItems = ruleItem.split(",");
            for (String string : ruleItems) {
                if (StringUtils.isNotBlank(item) && StringUtils.isNotBlank(string) && string.toUpperCase().equals(item.toUpperCase())) {
                    vali = true;
                    break;
                }
            }
        }
        LogUtil.getLogger(LogType.Ope).error(">>>输入字段的判断（可以判断ALL，带英文逗号分割的）{}<<<",vali);
        return vali;
    }

    /**
     *  * @Title: checkDLSCbyIandE
     *  * @Description: 判断某一字段的包含与不包含
     *  * @param: @param item
     *  * @param: @param include
     *  * @param: @param except
     *  * @param: @param ruleId
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkDLSCbyIandE(String item, String include, String except, int ruleId) {
        //是否可分发，默认为可分发
        boolean vali = false;
        //验证包含条件还是不包含条件，默认为包含条件
        boolean ruleFlag = false;
        String rule = "";
        if (StringUtils.isNotBlank(include) && StringUtils.isBlank(except)) {
            ruleFlag = true;
            rule = include.trim().toUpperCase();
        } else if (StringUtils.isBlank(include) && StringUtils.isNotBlank(except)) {
            ruleFlag = false;
            rule = except.trim().toUpperCase();
        } else {
            LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleId + "分发】---分发的规则不全，请及时查看。");
            return false;
        }

        //验证稿件是否可分发
        if (ruleFlag) {
            if (checkDistributeItem(item, rule)) {
                vali = true;
            }
        } else {
            if (!checkDistributeItem(item, rule)) {
                vali = true;
            }
        }
        LogUtil.getLogger(LogType.Ope).info(">>>判断某一字段的包含与不包含{},项目:{},include:{},except:{},ruleId:{}<<<",vali,item,include,except,ruleId);
        return vali;
    }

    /**
     *  * @Title: checkSfileDepbyIandE
     *  * @Description: 验证发稿部门
     *  * @param: @param itemlist
     *  * @param: @param include
     *  * @param: @param except
     *  * @param: @param ruleId
     *  * @param: @return   
     *  * @return: boolean   
     *  * @throws
     *  
     */
    public static boolean checkSfileDepbyIandE(List<String> itemlist, String include, String except, int ruleId) {
        //是否可分发，默认为可分发
        boolean vali = false;
        //验证包含条件还是不包含条件，默认为包含条件
        boolean ruleFlag = false;
        String rule = "";

//		if(FileUtils.isBlankList(itemlist)){
//			LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleId + "分发】---未定稿的发稿部门信息不全，请及时查看。");
//			return false;
//		}else 
        if (StringUtils.isNotBlank(include) && StringUtils.isBlank(except)) {
            ruleFlag = true;
            rule = include.trim().toUpperCase();
        } else if (StringUtils.isBlank(include) && StringUtils.isNotBlank(except)) {
            ruleFlag = false;
            rule = except.trim().toUpperCase();
        } else {
            LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleId + "分发】---分发的规则不全，请及时查看。");
            return false;
        }

        if (StringUtils.isNotBlank(include) && "ALL".equals(include.trim().toUpperCase())) {
            vali = true;
        } else {
            if (FileUtils.isBlankList(itemlist)) {
                LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleId + "分发】---未定稿的发稿部门信息不全，请及时查看。");
                return false;
            } else {
                if (ruleFlag) {
                    for (String item : itemlist) {
                        //验证是否可分发
                        if (checkDistributeItem(item, rule)) {
                            vali = true;
                            break;
                        }
                    }
                } else {
                    for (String item : itemlist) {
                        if (!checkDistributeItem(item, rule)) {
                            vali = true;
                            break;
                        }
                    }
                }
            }
        }
        LogUtil.getLogger(LogType.Ope).info(">>>验证发稿部门{},itemlist:{},include:{},except:{},ruleId:{}<<<",vali,itemlist,include,except,ruleId);
        return vali;
    }
}
