package com.bfd.monitor;

import com.bfd.monitor.controller.InitScheduler;
import com.bfd.monitor.utils.Constants;
import com.bfd.monitor.utils.LogType;
import com.bfd.monitor.utils.LogUtil;
import com.bfd.monitor.utils.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 *
  * @ClassName:     ApiFileDistributeService
  * @Description:入口
  * @author:    Android_Robot
  * @date:        2018年6月5日 下午4:54:12
  *
  *
  
 */
@Controller
//@Import(DynamicDataSourceRegister.class)
@SpringBootApplication
@MapperScan("src/main/java/com/bfd/monitor")
public class ApiFileDistributeService  implements ApplicationRunner {

    /**
     *
      * @Title: main
      * @Description: 主函数
      * @param: @param args   

      * @return: void   
      * @throws
      
     */
    public static void main(String[] args) {

        String LOG4J_HOME = "LOG4J_HOME";

        String log4jhome = System.getenv(LOG4J_HOME);

        if (StringUtils.isNotBlank(log4jhome)) {

            System.setProperty(LOG4J_HOME, log4jhome);

        }

        else {

            System.setProperty(LOG4J_HOME, ".");

        }

        ApplicationContext applicationContext = SpringApplication.run(ApiFileDistributeService.class, args);

        SpringContextUtil.setApplicationContext(applicationContext);

        if(Constants.DISTRIBUTE_INIT){

            // System.out.println("开始初始化线程");
            LogUtil.getLogger(LogType.Run).info("开始初始化线程");

            // 初始化启动已开启的文件分发规则集

            InitScheduler.startScheduler();

        }


    }

    /**
     *
      * <p>Title: run</p>
      * <p>Description: 运行</p>
      * @param args
      * @throws Exception
      * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
      
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}