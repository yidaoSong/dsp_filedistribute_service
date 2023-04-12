package com.bfd.monitor.bean;

import java.util.List;

/**
 * 
 * @ClassName:     LineColumn
 * @Description:线路栏目bean
 * @author:    Android_Robot
 * @date:        2018年6月6日 上午9:43:28
 *
 * 
 
 */
public class LineColumn {
	private String lineId;//线路的五位码
	private String lineName;//线路名称
	private String lineCid;//线路的CID
	private List<String> columnId;//栏目的后位码

	/**
	 * 
	 * @Title: getLineId
	 * @Description: 获取线路五位码
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getLineId() {
		return lineId;
	}
	
	/**
	 * 
	 * @Title: setLineId
	 * @Description: 线路五位码set 方法
	 * @param: @param lineId   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	/**
	 * 
	 * @Title: getLineName
	 * @Description: 线路名称的get方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getLineName() {
		return lineName;
	}
	
	/**
	 * 
	 * @Title: setLineName
	 * @Description: 线路名称的set方法
	 * @param: @param lineName   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	/**
	 * 
	 * @Title: getLineCid
	 * @Description: 线路cid的get 方法
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public String getLineCid() {
		return lineCid;
	}
	
	/**
	 * 
	 * @Title: setLineCid
	 * @Description: 线路cid的set 方法
	 * @param: @param lineCid   
	 * @return: void   
	 * @throws
	 
	 */
	public void setLineCid(String lineCid) {
		this.lineCid = lineCid;
	}
	
	/**
	 * 
	 * @Title: getColumnId
	 * @Description: 栏目cid的get 方法
	 * @param: @return   
	 * @return: List<String>   
	 * @throws
	 
	 */
	public List<String> getColumnId() {
		return columnId;
	}
	
	/**
	 * 
	 * @Title: setColumnId
	 * @Description: 栏目cid的set 方法
	 * @param: @param columnId   
	 * @return: void   
	 * @throws
	 
	 */
	public void setColumnId(List<String> columnId) {
		this.columnId = columnId;
	}
}
