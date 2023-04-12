package com.bfd.monitor.bean;

import com.bfd.monitor.utils.ConfigLoadUtil;

public class ConfigParam {
    private static String CONFIG_JSON_LIST = ConfigLoadUtil.getKeyToValue("config.json.list");

     public static String getConfigJsonList() {
            return CONFIG_JSON_LIST;
    }

    private static String CONFIG_JSON_PATH = ConfigLoadUtil.getKeyToValue("config.json.path");

        public static String getConfigJsonPath() {
            return CONFIG_JSON_PATH;
    }

}
