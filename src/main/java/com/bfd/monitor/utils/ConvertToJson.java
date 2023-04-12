package com.bfd.monitor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bfd.monitor.bean.RuleSets;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class ConvertToJson {
    //将规则集中的数据导出成json格式

    public static RuleSets exportAllSets(HttpServletResponse response, List<RuleSets> ruleSetsList, String fileName){

        RuleSets ruleSets = new RuleSets();

        ObjectMapper objectMapper = new ObjectMapper();

        if (ObjectUtils.isEmpty(ruleSetsList)){
            LogUtil.getLogger(LogType.Run).info("规则集中无任何信息");

            return ruleSets;

        }else {
            try {
                String jsonString = objectMapper.writeValueAsString(ruleSetsList);
                // 拼接文件完整路径// 生成json格式文件
                String fullPath = "/" + fileName;
                // 保证创建一个新文件
                File file = new File(fullPath);
                if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) { // 如果已存在,删除旧文件
                    file.delete();
                }
                file.createNewFile();//创建新文件
                //将字符串格式化为json格式
                jsonString = jsonFormat(jsonString);

                // 将格式化后的字符串写入文件

                Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

                write.write(jsonString);

                write.flush();

                write.close();

                FileInputStream fis = new FileInputStream(file);

                // 设置相关格式

                response.setContentType("application/force-download");

                // 设置下载后的文件名以及header

                response.setHeader("Content-Disposition", "attachment;filename="

                        .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

                response.setCharacterEncoding("utf-8");

                // 创建输出对象

                OutputStream os = response.getOutputStream();

                // 常规操作

                byte[] buf = new byte[1024];

                int len = 0;

                while((len = fis.read(buf)) != -1) {

                    os.write(buf, 0, len);

                }

                fis.close();
                os.close();     //一定要记得关闭输出流，不然会继续写入返回实体模型
                return ruleSets;
            } catch (Exception e) {
                // System.out.println(e.getMessage());
                LogUtil.getLogger(LogType.Run).info(e.getMessage());
                e.printStackTrace();
                return ruleSets;
            }
        }
    }
    //将字符串格式化为json格式的字符串
    public static String jsonFormat(String jsonString) {

        JSONObject object= JSONObject.parseObject(jsonString);

        jsonString = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);

        return jsonString;

    }
}
