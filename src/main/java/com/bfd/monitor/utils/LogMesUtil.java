package com.bfd.monitor.utils;

import com.bfd.monitor.bean.LineColumn;
import com.bfd.monitor.bean.Manuscript;

/**
 * 
 * @ClassName:     LogMesUtil
 * @Description:日志处理公共类
 * @author:    Android_Robot
 * @date:        2018年6月6日 下午3:47:14
 *
 * 
 
 */
public class LogMesUtil {
	/**
	 * 获取解析后的稿件信息
	 * 稿件GcsfshC000003_20171110_CBMSN0.xml,稿件属性:S,语种:C,稿件类型:M,系统编号:CB,发稿部门:Xxcabc
	 * @param manuscript
	 * @return
	 */
public static String getManuscriptMsg(Manuscript manuscript){
	StringBuffer res = new StringBuffer("");
	if(manuscript!= null){
		res.append("稿件"+manuscript.getFilename());
		res.append(",稿件属性:"+manuscript.getType());
		res.append(",语种:"+manuscript.getLanguage());
		res.append(",稿件类型:"+manuscript.getContenttype());
		res.append(",系统编号:"+manuscript.getSystemnum());
		
		//成品稿获取线路信息
		if ("F".equals(manuscript.getType())) {
			res.append(",发稿部门:"+manuscript.getDepartment());
			if(!FileUtils.isBlankList(manuscript.getLinelist())){
				res.append(",线路信息:");
				for (int i=0;i<manuscript.getLinelist().size();i++) {
					LineColumn line = manuscript.getLinelist().get(i);
					if(i>0){
						res.append(",");
					}
					res.append(line.getLineId());
					if(!FileUtils.isBlankList(line.getColumnId())){
						res.append("(");
						for (int j=0;j<line.getColumnId().size();j++) {
							if(j>0){
								res.append(",");
							}
							res.append(line.getColumnId().get(j));
						}
						res.append(")");
					}
				}
			}
		}else{//未定稿只获取发稿部门信息
			if(!FileUtils.isBlankList(manuscript.getDeplist())){
				res.append(",发稿部门:");
				for(int i=0;i<manuscript.getDeplist().size();i++){
					if(i>0){
						res.append(",");
					}
					res.append(manuscript.getDeplist().get(i));
				}
			}
		}
		
	}else{
		res.append("稿件信息为空，请及时查看。");
	}
	return res.toString();
}

/**
 * 
 * @Title: getDistributeMsg
 * @Description: 分发消息日志记录
 * @param: @param setname
 * @param: @param rulename
 * @param: @param filename
 * @param: @param time   
 * @return: void   
 * @throws
 
 */
public static void getDistributeMsg(String setname,String rulename,String filename,String time){
	 LogUtil.getLogger(LogType.Ope).info("【"+setname+"】("+rulename+")在"+time+"分发"+filename+"成功");
}

/**
 * 
 * @Title: getErrorDistributeMsg
 * @Description: 分发错误信息
 * @param: @param setname
 * @param: @param rulename
 * @param: @param filename
 * @param: @param time
 * @param: @param errorType   
 * @return: void   
 * @throws
 
 */
public static void getErrorDistributeMsg(String setname,String rulename,String filename,String time,int errorType){
	//错误标识
	String errorStr = "";
	
	//根据错误信息获取错误详情
    switch (errorType) {
        case 1: errorStr = " 未知原因,分发失败";break;
        case 2: errorStr = " 附件不全,分发失败";break;
        case 3: errorStr = " 盘满,分发失败";break;
        case 4: errorStr = " xml解析失败,分发失败";break;
        case 5: errorStr = " xml文件为空,分发失败";break;
        case 6: errorStr = " xml格式错误,分发失败";break;
        case 7:errorStr = " TEXT中文件缺失,分发失败";break;
        case 8:errorStr = " 附件分发到waiting目录失败";break;
        case 9:errorStr = " 分发到waiting目录失败";break;
        default: break;
    }
    
    //日志记录
    if(errorType ==8){
    	LogUtil.getLogger(LogType.Ope).error("【"+setname+"】("+rulename+")在"+time+"分发"+filename+"的附件失败");
    }else if(errorType==9){
    	LogUtil.getLogger(LogType.Ope).error("【"+setname+"】("+rulename+")在"+time+"分发"+filename+"失败");
    }else{
    	LogUtil.getLogger(LogType.Ope).error("【"+setname+"】稿件"+filename+errorStr);
    }
}
}
