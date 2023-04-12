package com.bfd.monitor.bean;

import java.util.List;

/**
 * 
 * @ClassName:     RuleSets
 * @Description:规则集bean
 * @author:    Android_Robot
 * @date:        2018年6月6日 上午10:29:28
 *
 * 
 
 */
public class RuleSets {
	private int id;// 主键
	private String setName;// 规则集名称
	private String setType;// 类型(0:消息,1:文件)
	private String rootCatalog;// 根目录
	private String catalog;// 分发目录/队列
	private String cataName;// 分发name目录
	private String cataText;// 分发text目录
	private String quartz;// 频率
	private int status;// 状态(0:启动,1:停止)
	//name路径
	private String namePath;
	//text路径
	private String textPath;
	//失败路径
	private String failedPath;
	//规则数量
	private int ruleCount;
	private List<Rule> ruleLs;//规则信息
	

	/**
	 * 
	 * @Title: getRuleCount
	 * @Description: 规则数量的get方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getRuleCount() {
		return ruleCount;
	}
/**
 * 
 * @Title: setRuleCount
 * @Description: 规则数量的set方法
 * @param: @param ruleCount   
 * @return: void   
 * @throws
 
 */
	public void setRuleCount(int ruleCount) {
		this.ruleCount = ruleCount;
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
	 * @Title: getNamePath
	 * @Description: name路径的get方法
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
	 * @Title: getId
	 * @Description: 主键的get 方法
	 * @param: @return   
	 * @return: int   
	 * @throws
	 
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @Title: setId
	 * @Description: 主键的set方法
	 * @param: @param id   
	 * @return: void   
	 * @throws
	 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @Title: getSetName
	 * @Description: 规则名名称的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * 
	 * @Title: setSetName
	 * @Description: 规则集名称的set方法
	 * @param: @param setName   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * 
	 * @Title: getSetType
	 * @Description: 规则集的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getSetType() {
		return setType;
	}

	/**
	 * 
	 * @Title: setSetType
	 * @Description: 规则集的set方法
	 * @param: @param setType   
	 * @return: void   
	 * @throws
	 
	 */
	public void setSetType(String setType) {
		this.setType = setType;
	}

	/**
	 * 
	 * @Title: getRootCatalog
	 * @Description:根路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getRootCatalog() {
		return rootCatalog;
	}

	/**
	 * 
	 * @Title: setRootCatalog
	 * @Description: 根路径的set方法
	 * @param: @param rootCatalog   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRootCatalog(String rootCatalog) {
		this.rootCatalog = rootCatalog;
	}

	/**
	 * 
	 * 
	 * @Title: getCatalog
	 * @Description: 路径的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getCatalog() {
		return catalog;
	}
	
    /**
     * 
     * @Title: setCatalog
     * @Description: 路径的set方法
     * @param: @param catalog   
     * @return: void   
     * @throws
     
     */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * 
	 * @Title: getCataName
	 * @Description: 名称的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getCataName() {
        return cataName;
    }
	
    /**
     * 
     * @Title: setCataName
     * @Description: 名称的set方法
     * @param: @param cataName   
     * @return: void   
     * @throws
     
     */
    public void setCataName(String cataName) {
        this.cataName = cataName;
    }

    /**
     * 
     * @Title: getCataText
     * @Description: 根路径的set方法
     * @param: @return   
     * @return: String   
     * @throws
     
     */
    public String getCataText() {
        return cataText;
    }

    /**
     * 
     * @Title: setCataText
     * @Description: 根路径的set方法
     * @param: @param cataText   
     * @return: void   
     * @throws
     
     */
    public void setCataText(String cataText) {
        this.cataText = cataText;
    }
    
    /**
     * 
     * @Title: getQuartz
     * @Description: 分发频率的get方法
     * @param: @return   
     * @return: String   
     * @throws
     
     */
	public String getQuartz() {
		return quartz;
	}

	/**
	 * 
	 * @Title: setQuartz
	 * @Description: 分发频率的set 方法
	 * @param: @param quartz   
	 * @return: void   
	 * @throws
	 
	 */
	public void setQuartz(String quartz) {
		this.quartz = quartz;
	}

	/**
	 * 
	 * @Title: getStatus
	 * @Description: 状态的get方法
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
	 * @Description: 状态的set方法
	 * @param: @param status   
	 * @return: void   
	 * @throws
	 
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 
	 * @Title: getRuleLs
	 * @Description: 规则列表的get方法
	 * @param: @return   
	 * @return: List<Rule>   
	 * @throws
	 
	 */
	public List<Rule> getRuleLs() {
		return ruleLs;
	}

	/**
	 * 
	 * @Title: setRuleLs
	 * @Description: 规则列表的set方法
	 * @param: @param ruleLs   
	 * @return: void   
	 * @throws
	 
	 */
	public void setRuleLs(List<Rule> ruleLs) {
		this.ruleLs = ruleLs;
	}

	@Override
	public String toString() {
		return "RuleSets{" +
				"id=" + id +
				", setName='" + setName + '\'' +
				", setType='" + setType + '\'' +
				", rootCatalog='" + rootCatalog + '\'' +
				", catalog='" + catalog + '\'' +
				", cataName='" + cataName + '\'' +
				", cataText='" + cataText + '\'' +
				", quartz='" + quartz + '\'' +
				", status=" + status +
				", namePath='" + namePath + '\'' +
				", textPath='" + textPath + '\'' +
				", failedPath='" + failedPath + '\'' +
				", ruleCount=" + ruleCount +
				", ruleLs=" + ruleLs +
				'}';
	}


}
