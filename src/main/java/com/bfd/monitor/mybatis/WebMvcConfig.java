package com.bfd.monitor.mybatis;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @ClassName:     WebMvcConfig
 * @Description:配置类
 * @author:    Android_Robot
 * @date:        2018年5月9日 上午10:04:29
 *
 * 
 
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
	/**
	 * 
	 * <p>Title: addInterceptors</p>
	 * <p>Description: 增加配置</p>
	 * @param registry
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }
}