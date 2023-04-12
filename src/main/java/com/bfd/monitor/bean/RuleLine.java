package com.bfd.monitor.bean;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     RuleLine
 * @Description:规则线路bean
 * @author:    Android_Robot
 * @date:        2018年6月6日 上午10:29:14
 *
 * 
 
 */
public class RuleLine {
	private int id;// 主键
	private int ruleId;// 规则表中的ID
	private String ruleName;// 规则表的名称，有唯一性验证
	private String lineCId;// 线路mongoDB里的cid值
	private String lineName;// 线路的名称：线路~中文~新华社成品库
	private int relation;// 对应关系：0包含条件； 1不包含条件
	private String columnCategory;// 栏目名称，以英文分好分割：栏目~新闻产品库~通稿新闻线路~外事;栏目~新闻产品库~通稿新闻线路~新闻人物
	private String lineCode;// 线路的五位码
	private String columnCodes;// 栏目的五位码，以英文逗号分割
	private String columnCids;// 栏目的CID

	private String[] category;// 栏目数组
	private String[] cateCode;// 栏目五位码数组
	private String[] cateCid;// 栏目的CID
	private String lineCategoryName;// 线路级联名称
	private List<Map<String, String>> columnlist;
	private String mediaTypeId;// 仅用于鉴权接口

	/**
	 * 
	 *  * @Title: getId  * @Description: 主键get方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 *  * @Title: setId  * @Description: 主键set方法 * @param: @param
	 * id     * @return: void     * @throws  
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 *  * @Title: getRuleId  * @Description: 规则主键get方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * 
	 *  * @Title: setRuleId  * @Description: 规则主键set方法
	 *  * @param: @param ruleId     * @return: void     * @throws  
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * 
	 *  * @Title: getRuleName  * @Description: 规则名get方法
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
	 *  * @Title: getLineCId  * @Description:线路主键get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getLineCId() {
		return lineCId;
	}

	/**
	 * 
	 *  * @Title: setLineCId  * @Description: 线路主键set方法
	 *  * @param: @param lineCId     * @return: void     * @throws  
	 */
	public void setLineCId(String lineCId) {
		this.lineCId = lineCId;
	}

	/**
	 * 
	 *  * @Title: getLineName  * @Description: 线路名称get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 
	 *  * @Title: setLineName  * @Description: 线路名称set方法
	 *  * @param: @param lineName     * @return: void     * @throws  
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 
	 *  * @Title: getRelation  * @Description: 关联关系get方法
	 *  * @param: @return     * @return: int     * @throws  
	 */
	public int getRelation() {
		return relation;
	}

	/**
	 * 
	 *  * @Title: setRelation  * @Description: 关联关系set方法
	 *  * @param: @param relation     * @return: void     * @throws  
	 */
	public void setRelation(int relation) {
		this.relation = relation;
	}

	/**
	 * 
	 *  * @Title: getColumnCategory  * @Description: 栏目级联编码get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getColumnCategory() {
		return columnCategory;
	}

	/**
	 * 
	 *  * @Title: setColumnCategory  * @Description: 栏目级联set方法
	 *  * @param: @param columnCategory     * @return: void     * @throws  
	 */
	public void setColumnCategory(String columnCategory) {
		this.columnCategory = columnCategory;
	}

	/**
	 * 
	 *  * @Title: getLineCode  * @Description: 线路编码get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getLineCode() {
		return lineCode;
	}

	/**
	 * 
	 *  * @Title: setLineCode  * @Description: 线路编码set方法
	 *  * @param: @param lineCode     * @return: void     * @throws  
	 */
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	/**
	 * 
	 *  * @Title: getColumnCodes  * @Description: 栏目编码get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getColumnCodes() {
		return columnCodes;
	}

	/**
	 * 
	 *  * @Title: setColumnCodes  * @Description: 栏目编码set方法
	 *  * @param: @param columnCodes     * @return: void     * @throws  
	 */
	public void setColumnCodes(String columnCodes) {
		this.columnCodes = columnCodes;
	}

	/**
	 * 
	 *  * @Title: getCategory  * @Description: 地址码的get方法
	 *  * @param: @return     * @return: String[]     * @throws  
	 */
	public String[] getCategory() {
		return category;
	}

	/**
	 * 
	 *  * @Title: setCategory  * @Description: 地址码的set方法
	 *  * @param: @param category     * @return: void     * @throws  
	 */
	public void setCategory(String[] category) {
		this.category = category;
	}

	/**
	 * 
	 *  * @Title: getLineCategoryName  * @Description: 线路级联名称的get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getLineCategoryName() {
		return lineCategoryName;
	}

	/**
	 * 
	 *  * @Title: setLineCategoryName  * @Description: 线路级联名称的set方法
	 *  * @param: @param lineCategoryName     * @return: void     * @throws  
	 */
	public void setLineCategoryName(String lineCategoryName) {
		this.lineCategoryName = lineCategoryName;
	}

	/**
	 * 
	 *  * @Title: getColumnlist  * @Description: 栏目数组的get方法
	 *  * @param: @return     * @return: List<Map<String,String>>     * @throws
	 *  
	 */
	public List<Map<String, String>> getColumnlist() {
		return columnlist;
	}

	/**
	 * 
	 *  * @Title: setColumnlist  * @Description: 栏目数组的set方法
	 *  * @param: @param columnlist     * @return: void     * @throws  
	 */
	public void setColumnlist(List<Map<String, String>> columnlist) {
		this.columnlist = columnlist;
	}

	/**
	 * 
	 *  * @Title: getMediaTypeId  * @Description: 类型的set方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getMediaTypeId() {
		return mediaTypeId;
	}

	/**
	 * 
	 *  * @Title: setMediaTypeId  * @Description: 类型的set方法
	 *  * @param: @param mediaTypeId     * @return: void     * @throws  
	 */
	public void setMediaTypeId(String mediaTypeId) {
		this.mediaTypeId = mediaTypeId;
	}

	/**
	 * 
	 *  * @Title: getCateCode  * @Description:五位码的get方法
	 *  * @param: @return     * @return: String[]     * @throws  
	 */
	public String[] getCateCode() {
		return cateCode;
	}

	/**
	 * 
	 *  * @Title: setCateCode  * @Description: 五位码的set方法
	 *  * @param: @param cateCode     * @return: void     * @throws  
	 */
	public void setCateCode(String[] cateCode) {
		this.cateCode = cateCode;
	}

	/**
	 * 
	 *  * @Title: getColumnCids  * @Description: 栏目cid的get方法
	 *  * @param: @return     * @return: String     * @throws  
	 */
	public String getColumnCids() {
		return columnCids;
	}

	/**
	 * 
	 *  * @Title: setColumnCids  * @Description: 栏目cid的set方法
	 *  * @param: @param columnCids     * @return: void     * @throws  
	 */
	public void setColumnCids(String columnCids) {
		this.columnCids = columnCids;
	}

	/**
	 * 
	 *  * @Title: getCateCid  * @Description: 线路cid的get方法
	 *  * @param: @return     * @return: String[]     * @throws  
	 */
	public String[] getCateCid() {
		return cateCid;
	}

	/**
	 * 
	 *  * @Title: setCateCid  * @Description: 线路cid的set方法
	 *  * @param: @param cateCid     * @return: void     * @throws  
	 */
	public void setCateCid(String[] cateCid) {
		this.cateCid = cateCid;
	}

}
