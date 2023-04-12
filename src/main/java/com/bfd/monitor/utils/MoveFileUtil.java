package com.bfd.monitor.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import com.bfd.monitor.bean.Manuscript;
import com.bfd.monitor.bean.RuleSets;

/**
 * 
 * @ClassName:     MoveFileUtil
 * @Description:文件编辑公共类
 * @author:    Android_Robot
 * @date:        2018年6月6日 下午3:54:01
 *
 * 
 
 */
public class MoveFileUtil {
	/**
	 * 读取指定目录下的文件
	 * @param dirPath 文件目录
	 * @return
	 */
	public static File[] readFileDir(String dirPath){
		if(!StringUtils.isNotBlank(dirPath)){
			return null;
		}
		if(!dirPath.endsWith("/")){
			dirPath = dirPath + "/";
		}
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		return files;
	}
	
	/**
	 * 删除文件
	 * @param filepath
	 */
	public static void deleteFile(String filepath){
		File file = new File(filepath);
		if(file.exists() && file.isFile()){
			file.delete();
		}
	}
	
	/**
	 * 删除分发name目录下稿件及附件文件
	 *
	 */
	public static void deleteManuscriptFileFromName(Manuscript manuscript, RuleSets ruleSet){
	    if(manuscript != null){
            List<String> metaPaths = manuscript.getMetaPaths();
            if(!FileUtils.isBlankList(metaPaths)){
                for (String string : metaPaths) {
                    String receiveMeta = ruleSet.getNamePath()+string;
                    //删除文件
                    deleteFile(receiveMeta);
                    LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---NAME目录下稿件"+manuscript.getFilename()+"的附件"+string+"已删除。");
                }
            }
            //下发目录
            String receivePath = ruleSet.getNamePath()+manuscript.getFilename();
            deleteFile(receivePath);
            LogUtil.getLogger(LogType.Run).info("--【rule_" + ruleSet.getId() + "分发】---NAME目录下稿件"+manuscript.getFilename()+"已删除。");
        }
	}
	
	/**
	 * 文件迁移
	 * 
	 * @param newFilePath：文件的新路径
	 * @param oldFilePath：原文件路径
	 * @param isDelete：是否删除原文件
	 * @return
	 */
	public static boolean moveFile(String newFilePath, String oldFilePath, boolean isDelete) {
		FileInputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int byteread = 0;
			File oldfile = new File(oldFilePath);
			if (oldfile.exists()) { // 文件存在时
			    inStream = new FileInputStream(oldfile); // 读入原文件
			    fs = new FileOutputStream(newFilePath);
			    byte[] buffer = new byte[4096];
			    while ((byteread = inStream.read(buffer)) != -1) {
			        fs.write(buffer, 0, byteread);
			    }
			    try {
					if (fs != null) {
						fs.close();
					}
					if (inStream != null) {
						inStream.close();
					}
				} catch (IOException e) {
					LogUtil.getLogger(LogType.Run).error(e.getMessage(), e);
				}
			    if (isDelete) {
			        oldfile.delete();// 删除原文件
			    }
			    return true;
			} else {
			    LogUtil.getLogger(LogType.Run).error("文件路径：" + oldFilePath + "不存在！");
			    return false;
			}
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(e.getMessage(), e);
			return false;
		} finally {//关闭文件流
			try {
				if (fs != null) {
					fs.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				LogUtil.getLogger(LogType.Run).error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 创建并写入文件
	 * @param file：文件实体
	 * @param content：文件内容
	 * @param fileCode：编码格式
	 * @return
	 */
    public static boolean createFile(File file, String content,String fileCode) {
        boolean vali = false;
        if(StringUtils.isBlank(fileCode)){
        	fileCode = "UTF-8";
        }
        //文件不存在则创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        Writer writer = null;
        try {
        	//创建新文件
            file.createNewFile();
            fos = new FileOutputStream(file);
            writer = new OutputStreamWriter(fos, fileCode);
            writer.write(content);
            vali = true;
        }
        catch (IOException e) {
        	LogUtil.getLogger(LogType.Run).error(e.getMessage(),e);
        }
        finally {//关闭文件流
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fos != null) { 
                    fos.close();
                }
            }
            catch (IOException e) {
            	LogUtil.getLogger(LogType.Run).error(e.getMessage(),e);
            }
        }
        return vali;
    }

}
