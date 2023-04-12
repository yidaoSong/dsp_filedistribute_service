package com.bfd.monitor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoadUtil {
    private static Logger log = LoggerFactory.getLogger(ConfigLoadUtil.class);

    /**
     * 系统配置文件在程序运行期间的路径
     */
    private static final String configFilePath = ConfigLoadUtil.class.getResource("/").getPath().replaceAll("%20", " ").concat("application.properties");

    private static Properties properties = new Properties();

    static {
        loadParam();
    }

    /**
     * 从配置文件中加载参数
     */
    private static void loadParam() {
        try {
            log.info("配置文件加载的路径：{}", configFilePath);
            File file = new File(configFilePath);
            InputStream in = new FileInputStream(file);
            // 加载配置文件
            properties.load(in);
        } catch (Exception e) {
            log.error("配置文件加载失败：{}", e.getMessage());
        }
    }

    /**
     * 读取配置文件
     */
    public static String getKeyToValue(String key) {
        return properties.getProperty(key);
    }

}
