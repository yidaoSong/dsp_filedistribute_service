package com.bfd.monitor.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.bfd.monitor.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Author:spq
 * Date:2022/12/814:43
 */
@Configuration
@EnableScheduling
@EnableAsync
public class LogCleanController {
    private static Logger logger = LoggerFactory.getLogger(LogCleanController.class);

    @Value("${retention.day}")
    private int retentionDay;

    @Autowired
    private RuleService ruleService;


    @Async
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 *7)
    public void cleanLogHalfYear() {
        logger.info("执行定时任务：每隔7天清理一次日志数据，保留近半年的数据");
        DateTime halfYear = DateUtil.offsetDay(DateUtil.date(), -retentionDay);
        String time = DateUtil.formatDate(halfYear);
        ruleService.cleanLogHalfYear(time);
    }
}
