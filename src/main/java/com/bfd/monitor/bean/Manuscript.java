package com.bfd.monitor.bean;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName:     Manuscript
 * @Description:稿件bean
 * @author:    Android_Robot
 * @date:        2018年6月6日 上午9:43:09
 *
 * 
 
 */
public class Manuscript {
	private int receiveMode;// 稿件的来源类型：0：消息；1：目录
	private int sendMode;//稿件的分发类型
	private String sendPath;//稿件的分发目录或队列
	
	private String contenttype;//稿件类型
	private String department;//发稿部门
	private List<String> deplist;//未定稿发稿部门
	private String systemnum;//系统编号
	private String language;//语种
	private List<LineColumn> linelist;//线路和栏目
	private String type;//稿件种类（F成品稿S待定稿）
	
	private String path;// 稿件的来源地址
	private String filename;// 稿件本地保存的文件名
	
	private List<String> metaPaths;//附件的文件名
	private Rule rule;//对应分发规则
	private String id;// 日志操作的ID
	private int ruleId;//分发规则的ID
	private int setId;//分发规则集的ID
	private int distributeType;//分发的类型：0：文件到文件；1：消息到消息；2：消息到文件
	private int distributeStatus;//分发状态：0：成功；1：未知失败；2：附件不全；3：盘满;4：xml解析失败；5：xml文件为空；6：xml格式错误；7：TEXT中文件缺失；8：附件分发到waiting目录失败；9：稿件分发到waiting目录失败
	private String distributeMsg;//分发结果
	private int distributeResult;//稿件分发状态：0：成功；1：失败；
	private String distributeResultMsg;//稿件分发结果
	private Date createTime;//分发时间
	
	/**
	 * 
	 * @Title: getDeplist
	 * @Description: 部门列表的get方法
	 * @param: @return   
	 * @return: List<String>   
	 * @throws
	 
	 */
	public List<String> getDeplist() {
		return deplist;
	}
	
	/**
	 * 
	 * @Title: setDeplist
	 * @Description: 部门列表的set方法
	 * @param: @param deplist   
	 * @return: void   
	 * @throws
	 
	 */
	public void setDeplist(List<String> deplist) {
		this.deplist = deplist;
	}
	
	/**
	 * 
	 * @Title: getSetId
	 * @Description: 规则主键的get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getSetId() {
		return setId;
	}
	
	/**
	 * 
	 * @Title: setSetId
	 * @Description:规则主键的set方法
	 * @param: @param setId   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSetId(int setId) {
		this.setId = setId;
	}
	
	/**
	 * 
	 * @Title: getId
	 * @Description: 主键的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @Title: setId
	 * @Description: 规则主键的set方法
	 * @param: @param id   
	 * @return: void   
	 * @throws
	 
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @Title: getReceiveMode
	 * @Description:接受类型的get 方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getReceiveMode() {
		return receiveMode;
	}
	
	/**
	 * 
	 * @Title: setReceiveMode
	 * @Description: 接受类型的set方法
	 * @param: @param receiveMode   
	 * @return: void   
	 * @throws
	 
	 */
	public void setReceiveMode(int receiveMode) {
		this.receiveMode = receiveMode;
	}
	
	/**
	 * 
	 * @Title: getSendMode
	 * @Description: 下发类型的get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getSendMode() {
		return sendMode;
	}
	
	/**
	 * 
	 * @Title: setSendMode
	 * @Description: 下发类型的set方法
	 * @param: @param sendMode   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSendMode(int sendMode) {
		this.sendMode = sendMode;
	}
	
	/**
	 * 
	 * @Title: getSendPath
	 * @Description: 下发路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getSendPath() {
		return sendPath;
	}
	
	/**
	 * 
	 * @Title: setSendPath
	 * @Description: 下发路径的set方法
	 * @param: @param sendPath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSendPath(String sendPath) {
		this.sendPath = sendPath;
	}
	
	/**
	 * 
	 * @Title: getContenttype
	 * @Description:稿件类型的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getContenttype() {
		return contenttype;
	}
	
	/**
	 * 
	 * @Title: setContenttype
	 * @Description: 稿件类型的set方法
	 * @param: @param contenttype   
	 * @return: void   
	 * @throws
	 
	 */
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	
	/**
	 * 
	 * @Title: getDepartment
	 * @Description: 部门get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * 
	 * @Title: setDepartment
	 * @Description: 部门的set方法
	 * @param: @param department   
	 * @return: void   
	 * @throws
	 
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * 
	 * @Title: getSystemnum
	 * @Description: 系统编号的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getSystemnum() {
		return systemnum;
	}
	
	/**
	 * 
	 * @Title: setSystemnum
	 * @Description: 系统编号的set方法
	 * @param: @param systemnum   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSystemnum(String systemnum) {
		this.systemnum = systemnum;
	}
	
	/**
	 * 
	 * @Title: getLanguage
	 * @Description: 语种的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * 
	 * @Title: setLanguage
	 * @Description: 语种的set方法
	 * @param: @param language   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * 
	 * @Title: getLinelist
	 * @Description: 线路列表的get方法
	 * @param: @return   
	 * @return: List<LineColumn>   
	 * @throws
	 
	 */
	public List<LineColumn> getLinelist() {
		return linelist;
	}
	
	/**
	 * 
	 * @Title: setLinelist
	 * @Description: 线路列表的set方法
	 * @param: @param linelist   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLinelist(List<LineColumn> linelist) {
		this.linelist = linelist;
	}
	
	/**
	 * 
	 * @Title: getPath
	 * @Description: 路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * 
	 * @Title: setPath
	 * @Description: 路径的set方法
	 * @param: @param path   
	 * @return: void   
	 * @throws
	 
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * 
	 * @Title: getFilename
	 * @Description: 文件名的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * 
	 * @Title: setFilename
	 * @Description: 文件名的set方法
	 * @param: @param filename   
	 * @return: void   
	 * @throws
	 
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * 
	 * @Title: getMetaPaths
	 * @Description: 路径的set方法
	 * @param: @return   
	 * @return: List<String>   
	 * @throws
	 
	 */
	public List<String> getMetaPaths() {
		return metaPaths;
	}
	
	/**
	 * 
	 * @Title: setMetaPaths
	 * @Description: 路径的set方法
	 * @param: @param metaPaths   
	 * @return: void   
	 * @throws
	 
	 */
	public void setMetaPaths(List<String> metaPaths) {
		this.metaPaths = metaPaths;
	}
	
	/**
	 * 
	 * @Title: getRule
	 * @Description: 规则的get方法
	 * @param: @return   
	 * @return: Rule   
	 * @throws
	 
	 */
	public Rule getRule() {
		return rule;
	}
	
	/**
	 * 
	 * @Title: setRule
	 * @Description: 规则的set方法
	 * @param: @param rule   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	/**
	 * 
	 * @Title: getDistributeMsg
	 * @Description: 分发详情的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getDistributeMsg() {
		return distributeMsg;
	}
	
	/**
	 * 
	 * @Title: setDistributeMsg
	 * @Description: 分发详情的set方法
	 * @param: @param distributeMsg   
	 * @return: void   
	 * @throws
	 
	 */
	public void setDistributeMsg(String distributeMsg) {
		this.distributeMsg = distributeMsg;
	}
	
	/**
	 * 
	 * @Title: getCreateTime
	 * @Description: 创建时间的set方法
	 * @param: @return   
	 * @return: Date   
	 * @throws
	 
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 
	 * @Title: setCreateTime
	 * @Description: 创建时间的set方法
	 * @param: @param createTime   
	 * @return: void   
	 * @throws
	 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 
	 * @Title: getRuleId
	 * @Description: 规则主键的get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getRuleId() {
		return ruleId;
	}
	
	/**
	 * 
	 * @Title: setRuleId
	 * @Description: 规则主键的set方法
	 * @param: @param ruleId   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	/**
	 * 
	 * @Title: getDistributeStatus
	 * @Description: 分发状态的get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getDistributeStatus() {
		return distributeStatus;
	}
	
	/**
	 * 
	 * @Title: setDistributeStatus
	 * @Description: 分发状态的set方法
	 * @param: @param distributeStatus   
	 * @return: void   
	 * @throws
	 
	 */
	public void setDistributeStatus(int distributeStatus) {
		this.distributeStatus = distributeStatus;
	}
	
	/**
	 * 
	 * @Title: getDistributeType
	 * @Description: 分发类型的set方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getDistributeType() {
		return distributeType;
	}
	
	/**
	 * 
	 * @Title: setDistributeType
	 * @Description: 分发类型的set方法
	 * @param: @param distributeType   
	 * @return: void   
	 * @throws
	 
	 */
	public void setDistributeType(int distributeType) {
		this.distributeType = distributeType;
	}
	
	/**
	 * 
	 * @Title: getType
	 * @Description: 类型的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @Title: setType
	 * @Description: 类型的set方法
	 * @param: @param type   
	 * @return: void   
	 * @throws
	 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @Title:        getDistributeResult <BR>
	 * @Description: please write your description <BR>
	 * @return:     int <BR>
	 */
	public int getDistributeResult() {
		return distributeResult;
	}

	/** 
	 * @Title:  setDistributeResult <BR> 
	 * @Description: please write your description <BR> 
	 * @return: int <BR> 
	 */
	public void setDistributeResult(int distributeResult) {
		this.distributeResult = distributeResult;
	}

	/**
	 * @Title:        getDistributeResultMsg <BR>
	 * @Description: please write your description <BR>
	 * @return:     String <BR>
	 */
	public String getDistributeResultMsg() {
		return distributeResultMsg;
	}

	/** 
	 * @Title:  setDistributeResultMsg <BR> 
	 * @Description: please write your description <BR> 
	 * @return: String <BR> 
	 */
	public void setDistributeResultMsg(String distributeResultMsg) {
		this.distributeResultMsg = distributeResultMsg;
	}
}
	