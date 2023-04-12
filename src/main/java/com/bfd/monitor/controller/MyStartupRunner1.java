package com.bfd.monitor.controller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName:     MyStartupRunner1
 * @Description:服务启动执行
 * @author:    Android_Robot
 * @date:        2018年9月18日 下午4:59:23
 *
 * 
 
 */
@Component
public class MyStartupRunner1 implements CommandLineRunner{

	/**
	 * 
	 * <p>Title: run</p>
	 * <p>Description: 服务启用</p>
	 * @param args
	 * @throws Exception
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 
	 */
    @Override
    public void run(String...args) throws Exception {
        // System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
    }
}