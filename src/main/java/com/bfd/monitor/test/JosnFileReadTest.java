/*
package com.bfd.monitor.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfd.monitor.common.vo.Result;
import com.bfd.monitor.utils.FileUtils;
import com.bfd.monitor.utils.StringUtils;

import java.io.*;


public class ReadJsonFile1 implements Serializable {

     static JSONArray jsonArray;

    public static JSONArray readJsonFile() {
        String jsonStr = "";
        try {
            //读取json文件
            File jsonFile = new File("E:\\Desktop\\SetsJsonFile.json");
            FileReader fileReader = new FileReader(jsonFile);
            //Boolean canReadJson = FileUtils.canRead(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            // if(!StringUtils.is)
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            //转换为字符串
            jsonStr = sb.toString();
            //判断读取的文件是否为空
            if (!StringUtils.isBlank(jsonStr)) {
                System.out.println("已加载" + jsonFile.getPath() + "目录下的Json文件");
                 jsonArray = JSON.parseArray(jsonStr); ////guanjian

            } else {
                System.out.println("Json文件为空，请到" + jsonFile.getPath() + "目录下查看Json文件");
            }
            //System.out.println(jsonStr);
            //将字符串转为JSON对象

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}

*/
