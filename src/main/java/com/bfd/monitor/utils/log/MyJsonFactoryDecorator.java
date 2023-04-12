package com.bfd.monitor.utils.log;

import net.logstash.logback.decorate.JsonFactoryDecorator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;

/**
 * 
 * @ClassName:     MyJsonFactoryDecorator
 * @Description:Json工厂类定义
 * @author:    Android_Robot
 * @date:        2018年6月11日 上午11:16:57
 *
 * 
 
 */
public class MyJsonFactoryDecorator implements JsonFactoryDecorator {
 
	/**
	 * 
	 * <p>Title: decorate</p>
	 * <p>Description: </p>
	 * @param factory
	 * @return
	 * @see net.logstash.logback.decorate.JsonFactoryDecorator#decorate(com.fasterxml.jackson.databind.MappingJsonFactory)
	 
	 */
    @Override
    public MappingJsonFactory decorate(MappingJsonFactory factory) {
    	// 禁用对非ascii码进行escape编码的特性
        factory.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        return factory;
    }
 
}