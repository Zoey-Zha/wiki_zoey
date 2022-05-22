package com.zoey.job;

import com.zoey.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DocJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Resource
    private DocService docService;

    /**
     * update at noon every day
     */
     @Scheduled(cron = "0 0 12 * * ??")
//    @Scheduled(cron = "0/5 * * * * ? ")
    public void cron() {
        LOG.info("update ebook every noon");
        long start = System.currentTimeMillis();
        docService.updateEbookInfo();
        // 关注下任务耗时
        LOG.info("updating finished, duration: {} 毫秒", System.currentTimeMillis() - start);
    }

}
