package com.bfd.monitor.utils;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.bean.Rule;
import com.bfd.monitor.bean.RuleSets;
import com.bfd.monitor.service.RuleService;

/**
 * 
 * @ClassName:     ErrorDualUtils
 * @Description:稿件分发错误处理功能类
 * @author:    Android_Robot
 * @date:        2018年6月6日 下午2:54:29
 *
 * 
 
 */
public class ErrorDualUtils {
	private static RuleService ruleService = (RuleService) SpringContextUtil.getBean("RuleService");
	/**
	 * 文件稿件的错误处理-稿件预处理
	 * @param filename
	 * @param ruleSet
	 * @param manuscript
	 * @param waitPath
	 */
	public static void dualErrorF(String filename,RuleSets ruleSet,Manuscript manuscript,String waitPath,Rule rule,int errorType){
		//文件名校验
		if(StringUtils.isBlank(filename) && manuscript!=null && StringUtils.isNotBlank(manuscript.getFilename())){
			filename = manuscript.getFilename();
		}else if(StringUtils.isBlank(filename) && manuscript==null){
			return;
		}
		if(manuscript != null){
			List<String> metaPaths = manuscript.getMetaPaths();
			if(!FileUtils.isBlankList(metaPaths)){
				for (String string : metaPaths) {
					//name路径
					String receiveMeta = ruleSet.getNamePath()+string;
					//分发失败路径
					String errorMeta = ruleSet.getFailedPath()+string;
					MoveFileUtil.moveFile(errorMeta, receiveMeta, true);
					if(StringUtils.isNotBlank(waitPath)){
						//删除wating目录中内容
						MoveFileUtil.deleteFile(waitPath+string);
					}
					LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---NAME目录下稿件"+manuscript.getFilename()+"的附件"+string+"已移送到FAILED目录中。");
				}
			}
		}else{
			//稿件信息初始赋值
			manuscript = new Manuscript();
			manuscript.setFilename(filename);
			manuscript.setSetId(ruleSet.getId());
		}
		//目录
		String receivePath = ruleSet.getNamePath()+filename;
		//分发失败目录
		String errorPath = ruleSet.getFailedPath()+filename;
		//稿件删除
		MoveFileUtil.moveFile(errorPath, receivePath, true);
		if(StringUtils.isNotBlank(waitPath)){
			MoveFileUtil.deleteFile(waitPath+filename);
		}
		
		manuscript.setDistributeType(0);
		manuscript.setDistributeStatus(errorType);
		String errorStr = "";
		//判断分发错误信息
		switch (errorType) {
			case 1: errorStr = "未知失败";break;
			case 2: errorStr = "附件不全";break;
			case 3: errorStr = "盘满";break;
			case 4: errorStr = "xml解析失败";break;
			case 5: errorStr = "xml文件为空";break;
			case 6: errorStr = "xml格式错误";break;
			case 7:errorStr = "TEXT中文件缺失";break;
			case 8:errorStr = "文件名格式错误";break;
			default: break;
		}
		manuscript.setDistributeMsg("稿件"+filename+errorStr+"，分发失败。");
		//分发错误日志表中插入信息
//		ruleService.insertDistributeLog(manuscript);
		String ruleName = rule==null?"":rule.getRuleName();
		//日志中记录错误信息
		 LogMesUtil.getErrorDistributeMsg(ruleSet.getSetName(),ruleName, filename, DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), errorType);
		LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---NAME目录下稿件"+manuscript.getFilename()+"已移送到FAILED目录中。");
	}
	
	/**
	 * 文件稿件的错误处理-分发
	 * @param filename
	 * @param ruleSet
	 * @param manuscript
	 * @param waitPath
	 */
	public static void dualDistributeErrorF(String filename,RuleSets ruleSet,Manuscript manuscript,String waitPath,Rule rule,int errorType){
	    //文件名校验
		if(StringUtils.isBlank(filename) && manuscript!=null && StringUtils.isNotBlank(manuscript.getFilename())){
	        filename = manuscript.getFilename();
	    }else if(StringUtils.isBlank(filename) && manuscript==null){
	        return;
	    }
	    if(manuscript != null){
	        List<String> metaPaths = manuscript.getMetaPaths();
	        if(!FileUtils.isBlankList(metaPaths)){
	            for (String string : metaPaths) {
	            	//name目录
	                String receiveMeta = ruleSet.getNamePath()+string;
	                //错误路径
	                String errorMeta = ruleSet.getFailedPath()+string;
	                File failed = new File(errorMeta);
	                if (!failed.exists()) {// 如果新路径下稿件附件不存在则执行复制操作
	                    MoveFileUtil.moveFile(errorMeta, receiveMeta, false);
                    }
	                //删除wating中稿件
	                if(StringUtils.isNotBlank(waitPath)){
	                    MoveFileUtil.deleteFile(waitPath+string);
	                }
	                LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---NAME目录下稿件"+manuscript.getFilename()+"的附件"+string+"已移送到FAILED目录中。");
	            }
	        }
	    }
	    String receivePath = ruleSet.getNamePath()+filename;
	    String errorPath = ruleSet.getFailedPath()+filename;
	    File failed = new File(errorPath);
        if (!failed.exists()) {// 如果新路径下稿件不存在则执行复制操作
            MoveFileUtil.moveFile(errorPath, receivePath, false);
        }
        //删除wating中稿件
	    if(StringUtils.isNotBlank(waitPath)){
	        MoveFileUtil.deleteFile(waitPath+filename);
	    }
	    manuscript.setDistributeType(0);
	    manuscript.setDistributeStatus(errorType);
	    String errorStr = "";
	    //根据错误类型判断错误信息
	    switch (errorType) {
	        case 1: errorStr = "未知失败";break;
	        case 2: errorStr = "附件不全";break;
	        case 3: errorStr = "盘满";break;
	        case 4: errorStr = "xml解析失败";break;
	        case 5: errorStr = "xml文件为空";break;
	        case 6: errorStr = "xml格式错误";break;
	        case 7:errorStr = "TEXT中文件缺失";break;
	        case 8:errorStr = "附件由目录：";
	        errorStr+=ruleSet.getRootCatalog()+ruleSet.getCatalog()+ruleSet.getCataName();
	        errorStr+="分发到目录：";
	        
	        if(StringUtils.isNotBlank(waitPath)){
	        	errorStr +=waitPath;
	        }
	        
	        errorStr +="失败";
	        		break;
	        case 9:errorStr = "由目录：";
	        errorStr+=ruleSet.getRootCatalog()+ruleSet.getCatalog()+ruleSet.getCataName();
	        errorStr+="分发到目录：";
	        
	        if(StringUtils.isNotBlank(waitPath)){
	        	errorStr +=waitPath;
	        }
	        
	        errorStr +="失败";
	        
	        break;
	        default: break;
	    }
	   LogMesUtil.getErrorDistributeMsg(ruleSet.getSetName(), rule.getRuleName(), filename, DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), errorType);
	    manuscript.setDistributeMsg("稿件"+filename+errorStr+"，分发失败。");
	    //稿件分发日志表添加数据
//	    ruleService.insertDistributeLog(manuscript);
	    LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---NAME目录下稿件"+manuscript.getFilename()+"已移送到FAILED目录中。");
	}
}
