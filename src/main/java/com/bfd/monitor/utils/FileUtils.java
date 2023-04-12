package com.bfd.monitor.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bfd.monitor.bean.LineColumn;
import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.service.RuleService;

/**
 * 
 * @ClassName:     FileUtils
 * @Description:文件处理公共类
 * @author:    Android_Robot
 * @date:        2018年6月6日 下午3:04:03
 *
 * 
 
 */
public class FileUtils {
	//规则服务接口
	private static RuleService ruleService = (RuleService) SpringContextUtil.getBean("RuleService");
	
	/**
	 * 
	 * @Title: getRecivedPath
	 * @Description: 获取分发目录的路径
	 * @param: @param rootPath
	 * @param: @param path
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public static String getRecivedPath(String rootPath,String path){
		if(StringUtils.isNotBlank(path) && StringUtils.isNotEmpty(rootPath)){
			if(rootPath.endsWith("/")){
				path = rootPath+path;
			}else{
				path = rootPath+"/"+path;
			}
			if(!path.endsWith("/")){ 
				path = path+"/";
			}
		}else if(StringUtils.isNotBlank(path)){
		    if (path.startsWith("/")) {
                path = path.substring(1);
            }
			if(!path.endsWith("/")){ 
				path = path+"/";
			}
		}else{
			path = "";
		}
		return path;
	}
 
	/**
	 * 
	 * @Title: canRead
	 * @Description: 判断文件或文件夹是否有读权限 
	 * @param: @param file
	 * @param: @return   
	 * @return: Boolean   
	 * @throws
	 
	 */
	public static Boolean canRead(File file) {  
		if (file.isDirectory()) { //文件是否存在
			try {  
				File[] listFiles = file.listFiles();  
				if (listFiles == null) { // 返回null表示无法读取或访问，如果为空目录返回的是一个空数组  
					return false;  
				} else {  
					return true;  
				}  
			} catch (Exception e) {  
				return false;  
			}  
		} else if (!file.exists()) { // 文件不存在  
			return false;  
		}  
		return checkRead(file);  
	}  
  
	/**
	 * 
	 * @Title: checkRead
	 * @Description: 检测文件是否有读权限 
	 * @param: @param file
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	private static boolean checkRead(File file) {  
		FileReader fd = null;  
		try {  
			fd = new FileReader(file);  
			while ((fd.read()) != -1) {  
				break;  
			}  
			return true;  
		} catch (IOException e) {  
			return false;  
		} finally {  
			try {
				if(fd != null){
					fd.close();
				}
			} catch (Exception e2) {
				LogUtil.getLogger(LogType.Run).error("IO流关闭异常："+e2.getMessage(), e2);
			}
		}  
	}  
  
	/**
	 * 
	 * @Title: canWrite
	 * @Description: 判断文件或文件夹是否有写权限 
	 * @param: @param file
	 * @param: @return   
	 * @return: Boolean   
	 * @throws
	 
	 */
	public static Boolean canWrite(File file) {  
		if (file.isDirectory()) {  
			try {  
				file = new File(file, "canWriteTestDeleteOnExit.temp");  
				if (file.exists()) {  
					//判断文件是否可写
					boolean checkWrite = checkWrite(file);  
					//判断文件是否可删除
					if (!deleteFile(file)) {  
						file.deleteOnExit();  
					}  
					return checkWrite;  
				} else if (file.createNewFile()) {  
					if (!deleteFile(file)) {  
						file.deleteOnExit();  
					}  
					return true;  
				} else {  
					return false;  
				}  
			} catch (Exception e) {  
				return false;  
			}  
		}  
		return checkWrite(file);  
	}  

	/**
	 * 
	 * @Title: checkWrite
	 * @Description:检测文件是否有写权限 
	 * @param: @param file
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	private static boolean checkWrite(File file) {  
		FileWriter fw = null;  
		boolean delete = !file.exists();  
		boolean result = false;  
		try {  
			fw = new FileWriter(file, true);  
			fw.write("");  
			fw.flush();  
			result = true;  
			return result;  
		} catch (IOException e) {  
			return false;  
		} finally { 
			try {
				if(fw!=null){
					fw.close();
				}
			} catch (IOException e) {
				LogUtil.getLogger(LogType.Run).error("IO流关闭异常："+e.getMessage(), e);
			}
			if (delete && result) {  
				deleteFile(file);  
			}  
		}  
	}  

	/**
	 * 
	 * @Title: deleteFile
	 * @Description: 删除文件，如果要删除的对象是文件夹，先删除所有子文件(夹)，再删除该文件 
	 * @param: @param file
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean deleteFile(File file) {  
		return deleteFile(file, true);  
	}  
 
	/**
	 * 
	 * @Title: deleteFile
	 * @Description: 删除文件，如果要删除的对象是文件夹，则根据delDir判断是否同时删除文件夹
	 * @param: @param file
	 * @param: @param delDir
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean deleteFile(File file, boolean delDir) {  
		if (!file.exists()) { // 文件不存在  
			return true;  
		}  
		if (file.isFile()) {  
			return file.delete();  
		} else {  
			boolean result = true;  
			File[] children = file.listFiles();  
			for (int i = 0; i < children.length; i++) { // 删除所有子文件和子文件夹  
				result = deleteFile(children[i], delDir);// 递归删除文件  
				if (!result) {  
					return false;  
				}  
			}  
			if (delDir) {  
				result = file.delete(); // 删除当前文件夹  
			}  
			return result;  
		}  
	} 

/**
 * 
 * @Title: isTypeOk
 * @Description: 类型判断
 * @param: @param type
 * @param: @return   
 * @return: boolean   
 * @throws
 
 */
	public static boolean isTypeOk(String type){
		boolean vali = false;
		if(StringUtils.isNotBlank(type)){
			if("F".equals(type.toUpperCase())){
				vali = true;
			}
		}
		return vali;
	}

	/**
	 * 
	 * @Title: mkdirs
	 * @Description: 校验文件夹路径是否存在，没有则创建
	 * @param: @param path   
	 * @return: void   
	 * @throws
	 
	 */
	public static void mkdirs(String path) {
		File allFile = new File(path);
		if (!allFile.isDirectory()) {
			allFile.mkdirs();
		}
	}
	
	/**
	 * 
	 * @Title: CreateMultilayerFile
	 * @Description: 创建多层文件夹
	 * @param: @param dir
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean CreateMultilayerFile(String dir){  
		try {  
			File dirPath = new File(dir);  
			//路径不存在则创建
			if ((!dirPath.exists())&&(!dirPath.isDirectory())) {  
				dirPath.mkdirs();  
			}  
		} catch (Exception e) {  
			LogUtil.getLogger(LogType.Run).error("【规则分发】创建多层目录"+dir+"操作出错: "+e.getMessage());
			return false;  
		}  
		return true;  
	}
	
	/**
	 * 
	 * @Title: isExistsFile
	 * @Description: 判断路径指向的文件是否存在且非目录
	 * @param: @param filePath
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean isExistsFile(String filePath){
		boolean vali = false;
		if(StringUtils.isNotBlank(filePath)){
			File file = new File(filePath);
			//路径存在则是文件
			if(file.exists() && file.isFile()){
				vali = true;
			}
		}
		return vali;
	}

	/**
	 * 
	 * @Title: getPath
	 * @Description: 获取目录合理路径
	 * @param: @param path
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public static String getPath(String path){
		if(!path.trim().endsWith("/")){
			path = path.trim()+"/";
		}
		return path;
	}

	/**
	 * 
	 * @Title: getColumnCodes
	 * @Description: 获取稿件线路的栏目字符串（逗号分割）
	 * @param: @param column
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public static String getColumnCodes(String column){
		StringBuffer res= new StringBuffer("");
		if(StringUtils.isBlank(column)){
			return "";
		}
		else {
			//正则表达式
			Pattern pattern = Pattern.compile("\\;[a-zA-Z]{5}[0-9]{1,}\\;");
			Matcher matcher = pattern.matcher(column);
			//匹配处理
			if(matcher.matches()){
				column = matcher.group(0).replace(";", "").trim();
				column = column.substring(5);
				res.append(column);
			}
		}
		return res.toString();
	}

	/**
	 * 
	 * @Title: getLineCode
	 * @Description: 获取规则中的线路五位码
	 * @param: @param line
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public static String getLineCode(String line){
		if(StringUtils.isBlank(line)){
			return "";
		}
		//线路编码数组
		String[] lineArr = line.split(";");
		String res="";
		String lost = "";
		for (String string : lineArr) {
			if(StringUtils.isNotBlank(string)){
				//正则表达式
				Pattern pattern = Pattern.compile("[a-zA-Z]{4,6}");
				Matcher matcher = pattern.matcher(string);
				//匹配处理方式
				if(matcher.matches()){
					res = string;
					break;
				}
				//未匹配处理方式
				if (StringUtils.isBlank(lost)) {
					lost = string;
				}
			}
		}
		if (StringUtils.isBlank(res)) {
			res = lost;
		}
		return res;
	}

	/**
	 * 
	 * @Title: isManuscript
	 * @Description: 判断文件名是否是合法规范的CN稿件
	 * @param: @param filename
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean isManuscript(String filename){
		boolean vali = false;
		String regEx = "[a-zA-Z]{7}[0-9]{6}\\_[0-9]{8}\\_[a-zA-Z]{5}[0-9]{1}\\.xml";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(filename);
		vali= matcher.matches();
		return vali;
	}

	/**
	 * 判断文件名是否符合cnml文件的正常命名长度
	 * @param filename
	 * @return
	 */
	public static boolean isNotErrorFileName(String filename){
		boolean vali = false;
		if(StringUtils.isNotBlank(filename)){
			vali = Pattern.matches(".*[a-zA-Z]{7}[0-9]{6}\\_[0-9]{8}\\_[a-zA-Z]{5}[0-9]{1}.*", filename);
		}
		return vali;
	}

	/**
	 * 
	 * @Title: getFileContent
	 * @Description: 获取文件内容
	 * @param: @param file
	 * @param: @return   
	 * @return: String   
	 * @throws
	 
	 */
	public static String getFileContent(File file){
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);

			String charaString = null;
			while ((charaString = br.readLine()) != null) {
				sb.append(charaString);
			}
		}
		catch (IOException e) {
			LogUtil.getLogger(LogType.Run).error("IO流异常！"+e.getMessage(),e);
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			}
			catch (IOException e) {
				LogUtil.getLogger(LogType.Run).error("IO流异常！"+e.getMessage(),e);
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @Title: xmlManuscript
	 * @Description: 解析稿件的XML（CNML版）
	 * @param: @param fileContent
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: Manuscript   
	 * @throws	 
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public static Manuscript xmlManuscript(String fileContent,Manuscript manuscript){
		try {
			//替换CDATA中内容 
			//用于解决CDATA中特殊字符解析出错，相应注释还有Constants.java和application.properties中的distribute.file.char
			fileContent = fileContent.replaceAll("<!\\[CDATA\\[.*?\\]\\]>", "<![CDATA["+Constants.DISTRIBUTE_FILE_CHAR+"]]>");
			Document doc = DocumentHelper.parseText(fileContent);
			Element root = doc.getRootElement();

			//发稿部门
			if(manuscript!= null && "S".equals(manuscript.getType().toUpperCase())){//未定稿处理
				List<String> deplist = new ArrayList<String>();
				Iterator<Element> sendtos = root.element("Envelop").elements("SentTo").iterator();
				while (sendtos.hasNext()) {
					Element sendto = sendtos.next();
					Iterator<Element> recipients = sendto.elements("GroupRecipient").iterator();
					while(recipients.hasNext()){
						Element recipient = recipients.next();
						Attribute lineAttr = recipient.attribute("topicRef");
						deplist.add(lineAttr.getValue());
					}
				}
				manuscript.setDeplist(deplist);
				//产品-线路栏目
			}else if(manuscript!= null && "F".equals(manuscript.getType().toUpperCase())){//成品稿处理
				List<LineColumn> lineList = new ArrayList<LineColumn>();
				Element products = root.element("Envelop").element("Products");
				Iterator<Element> productArr = products.elements("Product").iterator();
				while (productArr.hasNext()) {
					Element product = productArr.next();
					Attribute lineCode = product.attribute("productID");
					if (null != lineCode) {
						String attrVal = lineCode.getValue();
						LineColumn lineColumn = new LineColumn();
						lineColumn.setLineId(attrVal);
						List<String> columnList = new ArrayList<String>();
						Element columns = product.element("Columns");
						if(columns!=null){
							Iterator<Element> columnArr = columns.elements("Column").iterator();
							while (columnArr.hasNext()) {
								Element column = columnArr.next();
								Attribute columnCode = column.attribute("topicRef");
								if(null != columnCode){
									String attrColumnVal = columnCode.getValue();
									columnList.add(attrColumnVal.trim());
								}
							}
							if(!isBlankList(columnList)){
								lineColumn.setColumnId(columnList);
							}
						}
						lineList.add(lineColumn);
					}
				}
				if(!isBlankList(lineList)){
					manuscript.setLinelist(lineList);
				}
			}

			//附件
			Iterator<Element> itemArr=root.element("Items").elements("Item").iterator();
			List<String> metaList = new ArrayList<String>();
			while (itemArr.hasNext()) {
				Element item = itemArr.next();
				Element contents = item.element("Contents");
				if(contents!=null){
					Iterator<Element> contentItemArr = contents.elements("ContentItem").iterator();
					while (contentItemArr.hasNext()) {
						Element contentItem =  contentItemArr.next();
						Attribute attachName = contentItem.attribute("href");
						if (null != attachName) {
							String attrVal = attachName.getValue();
							metaList.add(attrVal);
						}
					}
				}
			}
			if(!isBlankList(metaList)){
				manuscript.setMetaPaths(metaList);
			}
			//校验获取数据中是否存在cdata数据
			//暂时验证属性中不能包含CDATA标签，去掉属性验证
			manuscript = valCdata(manuscript);
		} catch (DocumentException e) {
			LogUtil.getLogger(LogType.Run).error(manuscript.getFilename()+"XMLDocument解析异常！" + e.getMessage(), e);
			//稿件规则集表插入错误日志记录
			manuscript.setDistributeResult(1);
			manuscript.setDistributeResultMsg("稿件："+manuscript.getFilename()+" XMLDocument解析异常！" + e.getMessage());
			try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception ex) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,ex);
                            }
			manuscript = null;
		} catch (Exception e) {//添加其他异常捕获
			LogUtil.getLogger(LogType.Run).error(manuscript.getFilename()+"XML结构解析异常！" + e.getMessage(), e);
			//稿件规则集表插入错误日志记录
			manuscript.setDistributeResult(1);
			manuscript.setDistributeResultMsg("稿件："+manuscript.getFilename()+" XML结构解析异常！" + e.getMessage());
			try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception ex) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,ex);
                            }
			manuscript = null;
		}finally {
			return manuscript;
		}
	}
	
	/**
	 * 
	 * @Title: valCdata
	 * @Description: 校验是否存在CDATA数据，因无法在属性中添加cdata测试，当前方法未测试
	 * @param: @param manuscript
	 * @param: @return   
	 * @return: Manuscript   
	 * @throws
	 */
	private static Manuscript valCdata(Manuscript manuscript){
		//验证是否存在CDATA数据
		if(manuscript!=null){
			//验证正则表达式
			String exp = ".*"+Constants.DISTRIBUTE_FILE_CHAR+".*";
			//验证发稿部门中是否存在CDATA-属性值暂时去掉
			List<String> deplist = manuscript.getDeplist();
			if(deplist!=null&&deplist.size()>0){
				int depLen = deplist.size();
				String depStr = "";//发稿部门
				
				for(int k=0;k<depLen;k++){
					depStr = deplist.get(k);
					
					if(depStr!=null&&Pattern.matches(exp, depStr)){
						manuscript.setDistributeResult(1);
						manuscript.setDistributeResultMsg("稿件："+manuscript.getFilename()+" XMLDocument解析异常！原因为：发稿部门可能存在特殊字符。");
						try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
                            }
						return null;
					}
				}
			}
			//验证线路栏目中是否存在CDATA
			List<LineColumn> lineList = manuscript.getLinelist();
			
			if(lineList!=null&&lineList.size()>0){
				int lineLen = lineList.size();
				for(int l=0;l<lineLen;l++){
					LineColumn lineColumn = lineList.get(l);
					String lineId = "";//线路编码
					
					if(lineColumn!=null){
						lineId = lineColumn.getLineId();
						
						if(lineId!=null&&Pattern.matches(exp, lineId)){
							manuscript.setDistributeResult(1);
							manuscript.setDistributeResultMsg("稿件："+manuscript.getFilename()+" XMLDocument解析异常！原因为：线路编码中可能存在特殊字符。");
							try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
                            }
							return null;
						}
						List<String> columnList = lineColumn.getColumnId();
						if(columnList!=null&&columnList.size()>0){
							int columnLen = columnList.size();
							String columnId = "";
							
							for(int m=0;m<columnLen;m++){
								columnId = columnList.get(m);
								if(columnId!=null&&Pattern.matches(exp, columnId)){
									manuscript.setDistributeResult(1);
									manuscript.setDistributeResultMsg("稿件："+manuscript.getFilename()+" XMLDocument解析异常！原因为：栏目编码中可能存在特殊字符。");
									try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
                            }
									return null;
								}
							}
						}
					}
				}
			}
			
			//附件名称验证
			List<String> metaLs = manuscript.getMetaPaths();
			
			if(metaLs!=null&&metaLs.size()>0){
				int metaLen = metaLs.size();
				String metaName = "";
				
				for(int n=0;n<metaLen;n++){
					metaName = metaLs.get(n);
					
					if(metaName!=null&&Pattern.matches(exp, metaName)){
						manuscript.setDistributeResult(1);
						manuscript.setDistributeResultMsg("稿件："+manuscript.getFilename()+" XMLDocument解析异常！原因为：附件名称中可能存在特殊字符。");
						try {
                                ruleService.insertLog(0,manuscript);
                            } catch (Exception e) {
                                LogUtil.getLogger(LogType.Run).error("manuscript-{}-{}",manuscript,e);
                            }
						return null;
					}
				}
				
			}
		}
		
		return manuscript;
	}
	
	/**
	 * 
	 * @Title: isBlankList
	 * @Description: 判断list是否为空
	 * @param: @param list
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 
	 */
	public static boolean isBlankList(List<?> list){
		boolean vali = false;
		if(list == null || list.size()<1){
			vali = true;
		}
		return vali;
	} 
}
