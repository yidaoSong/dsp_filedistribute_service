package com.bfd.monitor.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 
 * @ClassName:     MyBatisMapperScannerConfig
 * @Description:MyBatis相关配置
 * @author:    Android_Robot
 * @date:        2018年5月9日 上午10:06:26
 *
 * 
 
 */
@Configuration
public class MyBatisMapperScannerConfig {
    
	/**
	 * 
	 * @Title: mapperScannerConfigurer
	 * @Description: mapper配置
	 * @param: @return   
	 * @return: MapperScannerConfigurer   
	 * @throws
	 
	 */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.bfd.monitor.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.bfd.monitor.mybatis.ApiBaseMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
    
}
