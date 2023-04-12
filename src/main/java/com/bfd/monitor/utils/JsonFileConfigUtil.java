package com.bfd.monitor.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.bfd.monitor.bean.ConfigParam;
import com.bfd.monitor.bean.RuleSets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFileConfigUtil {
    private static Logger log = LoggerFactory.getLogger(JsonFileConfigUtil.class);

    private static Map<String,List<Object>> CONFIGMAP = new HashMap<>();

    @Bean
    public static Map getJsonConfigMap() {
        //获取sets 或者rule  的json信息
        String configParam = ConfigParam.getConfigJsonList();
        log.info("application.properties配置文件中的config.json.list-{}", configParam);
        String[] configParamArr = configParam.split(",");
        //String json = null;
        String jsonStr = "";
        String prettyFormat= "";
        for (int i = 0; i < configParamArr.length; i++) {
            String currentLoopPath = ConfigParam.getConfigJsonPath();
            //获取文件全路径位置
            currentLoopPath = currentLoopPath + configParamArr[i] + ".json";
            if (!new File(currentLoopPath).exists()) {
                continue;
            }

            try {
                jsonStr = ResourceUtil.readUtf8Str(currentLoopPath);//读取这个路径下的内容
                //jsonStr.replace("\n", "\\n");
                // System.out.println(jsonStr);
            } catch (Exception e) {
                log.error("加载配置文件-{}-失败", currentLoopPath);
                continue;
            }
           List<Object> currentList = null;

            if (StringUtils.isBlank(jsonStr)) {
                log.error("加载配置文件成功-{}-但是获取的字符串为空", currentLoopPath);
            } else {
                //从json相关对象到java的实体方法
            currentList = new Gson().fromJson(jsonStr, List.class);
                //JsonArray asJsonArray = JsonParser.parseString(currentList.toString()).getAsJsonArray();
                //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                 //json = gson.toJson(asJsonArray);
                 //prettyFormat = toPrettyFormat(jsonStr);
                log.info("加载配置文件成功-{}-获取的配置文件信息可到相应路径下查看", currentLoopPath);
            }
            CONFIGMAP.put(configParamArr[i],currentList);
        }
//        log.info("加载的完整配置-{}", jsonStr);
        log.info("配置信息加载完毕");
        return CONFIGMAP;
    }

    /**
     * 格式化输出JSON字符串
     * @return 格式化后的JSON字符串
     */
    private static String toPrettyFormat(String json) {
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonArray);
    }

}
