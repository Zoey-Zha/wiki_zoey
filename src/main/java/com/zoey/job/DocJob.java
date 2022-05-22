package com.zoey.job;

import com.zoey.service.DocService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DocJob {

    // private static final Logger LOG = (Logger) LoggerFactory.getLogger(DocJob.class);

    @Resource
    private DocService docService;

    /**
     * update at noon every day
     */
    @Scheduled(cron = "0 0 12 * * ??")
    public void cron() {
        docService.updateEbookInfo();
    }

}
