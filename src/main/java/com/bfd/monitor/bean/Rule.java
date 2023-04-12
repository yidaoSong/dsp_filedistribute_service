package com.bfd.monitor.bean;

import java.util.List;

/**
 * 
 *  * @ClassName:     Rule  * @Description:规则bean  * @author:    Android_Robot
 *  * @date:        2018年6月6日 上午9:43:44  *  *  
 */
public class Rule {

	/**
	 * 主键
	 */
	private int id;
	// 规则名称
	private String ruleName;
	// 稿件源来稿方式
	private int receiveMode;
	// 分发方式
	private int sendMode;
	// 稿件源路径
	private String receivePath;
	// 分发目录路径
	private String sendPath;
	// 规则状态
	private int ruleStatus;
	// 使用状态
	private int status;
	// 下发系统
	private String system;
	// 稿件类型
	private String contenttype;
	// 发稿部门
	private String department;
	// 系统编号
	private String systemnum;
	// 语种
	private String language;
	// 稿件类型不包含条件
	private String contenttypeExclude;
	// 部门不包含条件
	private String departmentExclude;
	// 系统编号不包含条件
	private String systemnumExclude;
	// 语种不包含条件
	private String languageExclude;
	// 文件名-包含条件
	private String fileName;
	// 文件名-不包含条件
	private String fileNameExclude;
	// 创建时间
	private String createTime;

	private String errorPath;// 错误路径

	// private int ruleType;//规则类型：0：分发；1：鉴权
	// 包含条件中线路列表
	private List<RuleLine> includeLC;
	// 不包含条件中线路列表
	private List<RuleLine> exceptLC;
	// name路径
	private String namePath;
	// text路径
	private String textPath;
	// wait路径
	private String waitingPath;
	// 失败路径
	private String failedPath;

	private int setid;// 规则集ID
	private String setName;// 规则名称
	private String rootCatalog;// 来源根目录
	private String quartz;// 分发频率
	private int setStatus;// 规则状态
	private int attribute;// 稿件属性

	private RuleSets ruleSets;// 规则集信息
	private String rootSendPath;// 分发根路径

	/**
	 * 
	 *  * @Title: getSetName  * @Description: 规则名称get 方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * 
	 *  * @Title: setSetName  * @Description: 规则名称set方法
	 *  * @param: @param setName     * @return: void     * @throws  
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * 
	 *  * @Title: getRootCatalog  * @Description:根路径get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getRootCatalog() {
		return rootCatalog;
	}

	/**
	 * 
	 *  * @Title: setRootCatalog  * @Description: 根路径set方法
	 *  * @param: @param rootCatalog     * @return: void     * @throws  
	 */
	public void setRootCatalog(String rootCatalog) {
		this.rootCatalog = rootCatalog;
	}

	/**
	 * 
	 *  * @Title: getQuartz  * @Description: 分发频率get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getQuartz() {
		return quartz;
	}

	/**
	 * 
	 *  * @Title: setQuartz  * @Description: 分发频率set方法
	 *  * @param: @param quartz     * @return: void     * @throws  
	 */
	public void setQuartz(String quartz) {
		this.quartz = quartz;
	}

	/**
	 * 
	 *  * @Title: getSetStatus  * @Description: 规则状态get方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getSetStatus() {
		return setStatus;
	}

	/**
	 * 
	 *  * @Title: setSetStatus  * @Description: 规则状态set方法
	 *  * @param: @param setStatus     * @return: void     * @throws  
	 */
	public void setSetStatus(int setStatus) {
		this.setStatus = setStatus;
	}

	/**
	 * 
	 *  * @Title: getSetid  * @Description:规则主键get方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getSetid() {
		return setid;
	}

	/**
	 * 
	 *  * @Title: setSetid  * @Description: 规则主键set方法
	 *  * @param: @param setid     * @return: void     * @throws  
	 */
	public void setSetid(int setid) {
		this.setid = setid;
	}

	/**
	 * 
	 *  * @Title: getErrorPath  * @Description: 错误路径get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getErrorPath() {
		return errorPath;
	}

	/**
	 * 
	 *  * @Title: setErrorPath  * @Description: 错误路径set方法
	 *  * @param: @param errorPath     * @return: void     * @throws  
	 */
	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

	/**
	 * 
	 *  * @Title: getId  * @Description: 主键的get方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 *  * @Title: setId  * @Description: 主键的set方法 * @param: @param
	 * id     * @return: void     * @throws  
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 *  * @Title: getRuleName  * @Description: 规则名称get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * 
	 *  * @Title: setRuleName  * @Description: 规则名称set方法
	 *  * @param: @param ruleName     * @return: void     * @throws  
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * 
	 *  * @Title: getReceiveMode  * @Description: 规则路径set方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getReceiveMode() {
		return receiveMode;
	}

	/**
	 * 
	 * @Title: setReceiveMode
	 * @Description: 规则类型set方法
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
	 * @Description: 下发方式get方法
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
	 * @Description: 下发方式set方法
	 * @param: @param sendMode   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSendMode(int sendMode) {
		this.sendMode = sendMode;
	}

	/**
	 * 
	 * @Title: getReceivePath
	 * @Description: 接受路径get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getReceivePath() {
		return receivePath;
	}

	/**
	 * 
	 * @Title: setReceivePath
	 * @Description: 接受路径set方法
	 * @param: @param receivePath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setReceivePath(String receivePath) {
		this.receivePath = receivePath;
	}

	/**
	 * 
	 * @Title: getSendPath
	 * @Description: 下发路径get方法
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
	 * @Description: 下发路径set方法
	 * @param: @param sendPath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSendPath(String sendPath) {
		this.sendPath = sendPath;
	}

	/**
	 * 
	 * @Title: getRuleStatus
	 * @Description: 规则状态get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getRuleStatus() {
		return ruleStatus;
	}

	/**
	 * 
	 * @Title: setRuleStatus
	 * @Description: 规则状态set方法
	 * @param: @param ruleStatus   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRuleStatus(int ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	/**
	 * 
	 * @Title: getStatus
	 * @Description: 状态get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 
	 * @Title: setStatus
	 * @Description: 状态set方法
	 * @param: @param status   
	 * @return: void   
	 * @throws
	 
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 
	 * @Title: getSystem
	 * @Description: 系统编号get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * 
	 * @Title: setSystem
	 * @Description: 系统编号set方法
	 * @param: @param system   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * 
	 * @Title: getContenttype
	 * @Description: 稿件类型get方法
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
	 * @Description: 稿件类型set方法
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
	 * @Description: 部门set方法
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
	 * @Description: 系统编号set方法
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
	 * @Description: 系统编号set方法
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
	 * @Description: 语种get方法
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
	 * @Description: 语种get方法
	 * @param: @param language   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * 
	 * @Title: getContenttypeExclude
	 * @Description:语种不包含条件get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getContenttypeExclude() {
		return contenttypeExclude;
	}

	/**
	 * 
	 * @Title: setContenttypeExclude
	 * @Description: 语种不包含条件set方法
	 * @param: @param contenttypeExclude   
	 * @return: void   
	 * @throws
	 
	 */
	public void setContenttypeExclude(String contenttypeExclude) {
		this.contenttypeExclude = contenttypeExclude;
	}

	/**
	 * 
	 * @Title: getDepartmentExclude
	 * @Description: 部门不包含条件get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getDepartmentExclude() {
		return departmentExclude;
	}

	/**
	 * 
	 * @Title: setDepartmentExclude
	 * @Description: 部门不包含条件的set方法
	 * @param: @param departmentExclude   
	 * @return: void   
	 * @throws
	 
	 */
	public void setDepartmentExclude(String departmentExclude) {
		this.departmentExclude = departmentExclude;
	}

	/**
	 * 
	 * @Title: getSystemnumExclude
	 * @Description: 系统编号不不包含条件的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getSystemnumExclude() {
		return systemnumExclude;
	}

	/**
	 * 
	 * @Title: setSystemnumExclude
	 * @Description: 系统编号不包含条件的set方法
	 * @param: @param systemnumExclude   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSystemnumExclude(String systemnumExclude) {
		this.systemnumExclude = systemnumExclude;
	}
	
    /**
     * 
     * @Title: getLanguageExclude
     * @Description: 语种不包含条件get方法
     * @param: @return   
     * @return: String   
     * @throws
     
     */
	public String getLanguageExclude() {
		return languageExclude;
	}

	/**
	 * 
	 * @Title: setLanguageExclude
	 * @Description: 语种不包含条件set方法
	 * @param: @param languageExclude   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLanguageExclude(String languageExclude) {
		this.languageExclude = languageExclude;
	}

	/**
	 * 
	 * @Title: getCreateTime
	 * @Description: 创建时间的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getCreateTime() {
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
	public void setCreateTime(String createTime) {
		this.createTime = createTime.substring(0, createTime.length() - 1);
	}

	/**
	 * 
	 * @Title: getIncludeLC
	 * @Description: 包含条件线路的get方法
	 * @param: @return   
	 * @return: List<RuleLine>   
	 * @throws
	 
	 */
	public List<RuleLine> getIncludeLC() {
		return includeLC;
	}

	/**
	 * 
	 * @Title: setIncludeLC
	 * @Description: 包含条件线路的set方法
	 * @param: @param includeLC   
	 * @return: void   
	 * @throws
	 
	 */
	public void setIncludeLC(List<RuleLine> includeLC) {
		this.includeLC = includeLC;
	}

	/**
	 * 
	 * @Title: getExceptLC
	 * @Description: 不包含条件线路的get方法
	 * @param: @return   
	 * @return: List<RuleLine>   
	 * @throws
	 
	 */
	public List<RuleLine> getExceptLC() {
		return exceptLC;
	}

	/**
	 * 
	 * @Title: setExceptLC
	 * @Description: 不包含条件线路的set方法
	 * @param: @param exceptLC   
	 * @return: void   
	 * @throws
	 
	 */
	public void setExceptLC(List<RuleLine> exceptLC) {
		this.exceptLC = exceptLC;
	}

	
/**
 * 
 * <p>Title: toString</p>
 * <p>Description: 转换为string字符串</p>
 * @return
 * @see java.lang.Object#toString()
 
 */
	@Override
	public String toString() {
		return "Rule [id=" + id + ", ruleName=" + ruleName + ", receiveMode=" + receiveMode + ", sendMode=" + sendMode
				+ ", receivePath=" + receivePath + ", sendPath=" + sendPath + ", ruleStatus=" + ruleStatus + ", status="
				+ status + ", system=" + system + ", contenttype=" + contenttype + ", department=" + department
				+ ", systemnum=" + systemnum + ", language=" + language + ", contenttypeExclude=" + contenttypeExclude
				+ ", departmentExclude=" + departmentExclude + ", systemnumExclude=" + systemnumExclude
				+ ", languageExclude=" + languageExclude + ", createTime=" + createTime + "]";
	}

	/**
	 * 
	 * @Title: getNamePath
	 * @Description: name路径get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getNamePath() {
		return namePath;
	}

	/**
	 * 
	 * @Title: setNamePath
	 * @Description: name路径的set方法
	 * @param: @param namePath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	/**
	 * 
	 * @Title: getTextPath
	 * @Description: text路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getTextPath() {
		return textPath;
	}

	/**
	 * 
	 * @Title: setTextPath
	 * @Description: text路径的set方法
	 * @param: @param textPath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setTextPath(String textPath) {
		this.textPath = textPath;
	}

	/**
	 * 
	 * @Title: getWaitingPath
	 * @Description: wait路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getWaitingPath() {
		return waitingPath;
	}

	/**
	 * 
	 * @Title: setWaitingPath
	 * @Description: wait路径的set方法
	
	 * @param: @param waitingPath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setWaitingPath(String waitingPath) {
		this.waitingPath = waitingPath;
	}

	/**
	 * 
	 * @Title: getFailedPath
	 * @Description: 失败路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getFailedPath() {
		return failedPath;
	}

	/**
	 * 
	 * @Title: setFailedPath
	 * @Description: 失败路径的set方法
	 * @param: @param failedPath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setFailedPath(String failedPath) {
		this.failedPath = failedPath;
	}

	/**
	 * 
	 * @Title: getAttribute
	 * @Description: 稿件属性set方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getAttribute() {
		return attribute;
	}

	/**
	 * 
	 * @Title: setAttribute
	 * @Description: 稿件属性set方法
	 * @param: @param attribute   
	 * @return: void   
	 * @throws
	 
	 */
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	/**
	 * 
	 * @Title: getRuleSets
	 * @Description: 规则集get方法
	 * @param: @return   
	 * @return: RuleSets   
	 * @throws
	 
	 */
	public RuleSets getRuleSets() {
		return ruleSets;
	}

	/**
	 * 
	 * @Title: setRuleSets
	 * @Description: 规则集set 方法
	 * @param: @param ruleSets   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRuleSets(RuleSets ruleSets) {
		this.ruleSets = ruleSets;
	}

	/**
	 * 
	 * @Title: getRootSendPath
	 * @Description: 下发根路径get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getRootSendPath() {
		return rootSendPath;
	}

	/**
	 * 
	 * @Title: setRootSendPath
	 * @Description: 下发根路径set方法
	 * @param: @param rootSendPath   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRootSendPath(String rootSendPath) {
		this.rootSendPath = rootSendPath;
	}

	/**
	 * 
	 * @Title: getFileName
	 * @Description: 文件名get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @Title: setFileName
	 * @Description: 文件名set方法
	 * @param: @param fileName   
	 * @return: void   
	 * @throws
	 
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 * @Title: getFileNameExclude
	 * @Description: 文件名不包含条件get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getFileNameExclude() {
		return fileNameExclude;
	}

	/**
	 * 
	 * @Title: setFileNameExclude
	 * @Description: 文件名不包含条件set方法
	 * @param: @param fileNameExclude   
	 * @return: void   
	 * @throws
	 
	 */
	public void setFileNameExclude(String fileNameExclude) {
		this.fileNameExclude = fileNameExclude;
	}
}
