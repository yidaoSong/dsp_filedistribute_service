package com.bfd.monitor.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.distribute.DistributeService;
import com.bfd.monitor.service.RuleService;

/**
 * 
 * @ClassName:     DistributeUtil
 * @Description:分发公共类
 * @author:    Android_Robot
 * @date:        2018年6月6日 下午2:47:17
 *
 * 
 
 */
public class DistributeUtil {
	private static RuleService ruleService = (RuleService) SpringContextUtil.getBean("RuleService");
	/**
	 * 判断规则的目录是否有读写权限
	 * @param setid
	 * @param setname
	 * @return
	 */
	public static String checkRuleDirectoryError(int setid,String setname){
		StringBuffer sBuffer = new StringBuffer("");
		Rule ruleDemo = new Rule();
		//规则主键赋值
		ruleDemo.setSetid(setid);
		//分发类型赋值
		ruleDemo.setSendMode(1);
		//下发类型赋值
		ruleDemo.setReceiveMode(1);
		//根据条件查询规则
		List<Rule> rulelist = ruleService.queryRuleBySet(ruleDemo);
		
		if(!FileUtils.isBlankList(rulelist)){
			for (Rule rule : rulelist) {
				//下发路径
				String sPath = rule.getSendPath();
				//下发根目录
				String rootsendPath=rule.getRootSendPath()==null?Constants.DISTRIBUTE_PATH_SEND:rule.getRootSendPath();

				if(StringUtils.isNotBlank(sPath)){
					//拼接下发路径
					sPath = FileUtils.getRecivedPath(rootsendPath, sPath);

					//规则集启动时，系统不再校验创建waiting，failed，complete等目录
					FileUtils.CreateMultilayerFile(sPath);
					File sPathFile = new File(sPath);
					
					if(!FileUtils.canRead(sPathFile)&&FileUtils.canWrite(sPathFile)){
						LogUtil.getLogger(LogType.Ope).error("规则集"+setname+"的下发目录"+rule.getSendPath()+"没有读写权限，请及时处理！");
						LogUtil.getLogger(LogType.Run).error("--【rule_"+setid+"分发】---waiting目录"+sPath+"无操作权限，请及时联系平台管理员。");
						sBuffer.append(rule.getSendPath()+",");
						try {
							ruleService.updateRuleSwitch(1, rule.getId());
						} catch (Exception e) {
							LogUtil.getLogger(LogType.Run).error("数据库目前无法使用,无法操作数据库修改状态");
						}
					}
				}
			}
		}
		return sBuffer.toString();
	}
	
	/**
	 * 判断规则的目录是否有读写权限
	 * @param setid
	 * @param setname
	 * @return
	 */
	public static String checkRuleDirectoryErrorNoStatus(int setid,String setname){
		StringBuffer sBuffer = new StringBuffer("");
		Rule ruleDemo = new Rule();
		ruleDemo.setSetid(setid);ruleDemo.setSendMode(1);ruleDemo.setReceiveMode(1);
		
		//根据条件查询规则
		List<Rule> rulelist = ruleService.queryRuleBySetNoStatus(ruleDemo);
		if(!FileUtils.isBlankList(rulelist)){
			for (Rule rule : rulelist) {
				//获取下发路径
				String sPath = rule.getSendPath();
				//获取下发根路径
				String rootsendPath=rule.getRootSendPath()==null?Constants.DISTRIBUTE_PATH_SEND:rule.getRootSendPath();

				if(StringUtils.isNotBlank(sPath)){
					sPath = FileUtils.getRecivedPath(rootsendPath, sPath);
					//规则集启动时，系统不再校验创建waiting，failed，complete等目录
					FileUtils.CreateMultilayerFile(sPath);
					File sPathFile = new File(sPath);
					
					if(!(FileUtils.canRead(sPathFile)&&FileUtils.canWrite(sPathFile))){
						LogUtil.getLogger(LogType.Ope).error("规则集"+setname+"的下发目录"+rule.getSendPath()+"没有读写权限，请及时处理！");
						LogUtil.getLogger(LogType.Run).error("--【rule_"+setid+"分发】---下发目录"+sPath+"无操作权限，请及时联系平台管理员。");
						sBuffer.append(rule.getSendPath()+",");
					}
				}
			}
		}
		return sBuffer.toString();
	}
	
	
	/**
	 * 判断规则集的目录是否有读写权限
	 * @param ruleSet
	 * @return
	 */
	public static String checkSetDirectoryError(RuleSets ruleSet){
		StringBuffer errorMessage = new StringBuffer();
		if("1".equals(ruleSet.getSetType())){
			//根据根路径和下发路径拼接绝对下发路径
			String rPath = FileUtils.getRecivedPath(ruleSet.getRootCatalog(), ruleSet.getCatalog());
			//name路径
			String cataName = FileUtils.getRecivedPath(null, ruleSet.getCataName());
			//text路径
            String cataText = FileUtils.getRecivedPath(null, ruleSet.getCataText());
			String namePath = rPath+cataName;
			String textPath = rPath+cataText;
			//分发失败路径
			String failedPath = rPath+Constants.DISTRIBUTE_FOLDER_FAILED;
			ruleSet.setNamePath(namePath);
			ruleSet.setTextPath(textPath);
			ruleSet.setFailedPath(failedPath);

			//校验并创建TEXT、NAME、FAILED目录
			FileUtils.CreateMultilayerFile(ruleSet.getNamePath());
			FileUtils.CreateMultilayerFile(ruleSet.getTextPath());
			FileUtils.CreateMultilayerFile(ruleSet.getFailedPath());

			//判断来源稿件的目录是否有操作权限
			File nameFile = new File(ruleSet.getNamePath());
			//name目录无无读写权限
			if(!(FileUtils.canRead(nameFile)&&FileUtils.canWrite(nameFile))){
				LogUtil.getLogger(LogType.Ope).error("规则集"+ruleSet.getSetName()+"的name目录没有读写权限，请及时处理！");
				LogUtil.getLogger(LogType.Run).error("--【rule_"+ruleSet.getId()+"分发】---name目录"+ruleSet.getNamePath()+"无操作权限，请及时联系平台管理员。");
				errorMessage.append("--name目录 "+ruleSet.getNamePath()+" 无操作权限");
			}
			//text目录无无读写权限
			File textFile = new File(ruleSet.getTextPath());
			if(!(FileUtils.canRead(textFile)&&FileUtils.canWrite(textFile))){
				LogUtil.getLogger(LogType.Ope).error("规则集"+ruleSet.getSetName()+"的text目录没有读写权限，请及时处理！");
				LogUtil.getLogger(LogType.Run).error("--【rule_"+ruleSet.getId()+"分发】---text目录"+ruleSet.getTextPath()+"无操作权限，请及时联系平台管理员。");
				errorMessage.append("--text目录 "+ruleSet.getTextPath()+" 无操作权限");
			}
			//分发失败目录无无读写权限
			File failedFile = new File(ruleSet.getFailedPath());
			if(!(FileUtils.canRead(failedFile)&&FileUtils.canWrite(failedFile))){
				LogUtil.getLogger(LogType.Ope).error("规则集"+ruleSet.getSetName()+"的failed目录没有读写权限，请及时处理！");
				LogUtil.getLogger(LogType.Run).error("--【rule_"+ruleSet.getId()+"分发】---failed目录"+ruleSet.getFailedPath()+"无操作权限，请及时联系平台管理员。");
				errorMessage.append("--failed目录 "+ruleSet.getFailedPath()+" 无操作权限");
			}
		}
		return errorMessage.toString();
	}

	/**
	 * 
	 * @Title: checkManuscriptInfo
	 * @Description: 校验稿件信息
	 * @param: @param manuscript
	 * @param: @param textfile
	 * @param: @param ruleSet
	 * @param: @return   
	 * @return: Manuscript   
	 * @throws
	 
	 */
	public static Manuscript checkManuscriptInfo(Manuscript manuscript,File textfile,RuleSets ruleSet){
		//获取稿件路径
		String fileContent = FileUtils.getFileContent(textfile);
		if(StringUtils.isBlank(fileContent)){
			//稿件路径为空错误处理
			ErrorDualUtils.dualErrorF(textfile.getName(), ruleSet, manuscript, null,null,5);
			//稿件规则集表插入错误日志记录
			manuscript.setDistributeResult(1);
			manuscript.setDistributeResultMsg("TEXT目录"+ruleSet.getTextPath()+"中文件"+textfile.getName()+"内容为空。");
			try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
                            }
			manuscript = null;
			/*LogUtil.getLogger(LogType.Ope).error("*********稿件"+textfile.getName()+"内容为空*********");*/
			LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---TEXT目录"+ruleSet.getTextPath()+"中文件"+textfile.getName()+"内容为空。");
		}else if (!fileContent.startsWith("<?xml")) {//稿件头文件校验
			//稿件规则集表插入错误日志记录
			manuscript.setDistributeResult(1);
			manuscript.setDistributeResultMsg("TEXT目录"+ruleSet.getTextPath()+"中文件"+textfile.getName()+"的XML格式错误，无法解析。");
			try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
                            }
			ErrorDualUtils.dualErrorF(textfile.getName(), ruleSet, manuscript, null,null,6);
			manuscript = null;
			/*LogUtil.getLogger(LogType.Ope).error("*********稿件"+textfile.getName()+"的XML格式错误*********");*/
			LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---TEXT目录"+ruleSet.getTextPath()+"中文件"+textfile.getName()+"的XML格式错误，无法解析。");
		}else{
			//获取稿件内容
			manuscript = FileUtils.xmlManuscript(fileContent, manuscript);
			//判断稿件的附件是否完整
			if(manuscript!=null){
				boolean metaVali = DistributeService.checkMetaComplete(manuscript, ruleSet);
				if(!metaVali){
//					ruleService.errorLog(ruleSet, manuscript);
					try {
						ruleService.errorLog(ruleSet, manuscript);
					} catch (Exception e) {
						LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
					}
					LogUtil.getLogger(LogType.Run).error("--【rule_" + ruleSet.getId() + "分发】---TEXT目录"+ruleSet.getTextPath()+"中文件"+manuscript.getFilename()+"附件不全，暂不分发。");
					manuscript = null;					
				}
			}else{
				//XML解析异常
				ErrorDualUtils.dualErrorF(textfile.getName(), ruleSet, manuscript, null,null,4);
				manuscript = null;
			}
		}
		return manuscript;
	}

	/**
	 * 获取文件对文件的分发规则
	 * @param ruleSet
	 * @return
	 */
	public static List<Rule> getRuleBySetFtoF(RuleSets ruleSet){
		Rule ruleDemo = new Rule();
		ruleDemo.setSetid(ruleSet.getId());
		ruleDemo.setReceiveMode(1);ruleDemo.setSendMode(1);//文件对文件的
		//根据条件获取分发规则
		///根据规则集主键查询所有分发规则
		List<Rule> rulelist = ruleService.queryRuleAllBySet(ruleDemo);//已经判断加载方式
		List<Rule> reslist = new ArrayList<Rule>();
		if(!FileUtils.isBlankList(rulelist)){
			for (Rule rule : rulelist) {
				//下发路径
				String sPath = rule.getSendPath();
				//下发根路径
				String rootsendPath=rule.getRootSendPath()==null?Constants.DISTRIBUTE_PATH_SEND:rule.getRootSendPath();

				if(StringUtils.isNotBlank(sPath)){
					//绝对路径拼接
					sPath = FileUtils.getRecivedPath(rootsendPath, sPath);
					rule.setWaitingPath(sPath);
					//获取规则对应的线路信息
					rule = ruleService.getRuleLine(rule); // 已经判断加载方式
					reslist.add(rule);
				}
			}
		}
		return reslist;
	}
}