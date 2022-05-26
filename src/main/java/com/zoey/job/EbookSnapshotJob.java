package com.zoey.job;

import com.zoey.mapper.EbookSnapshotMapperCust;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
// 刚开始少了这个注解，所以即使SQL有error，也是正常运行
// 这里使用的是@Component注解，那么为什么不用Service，我怎么知道各个注解的不同用处
@Component
public class EbookSnapshotJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Resource
    EbookSnapshotMapperCust ebookSnapshotMapperCust;

    @Resource
    private SnowFlake snowFlake;

//    @Scheduled(cron = "0 0 12 * * ?")
    @Scheduled(cron = "0 0/30 * * * ?")
//    @Transactional 这里需要这个注解吗？因为只有一个方法，所有SQL都在一个方法里
    public void cron() {
        // 增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        LOG.info("update ebookSnapshot every five seconds");
        long start = System.currentTimeMillis();
        ebookSnapshotMapperCust.getEbookSnapshot();
        // 关注下任务耗时
        LOG.info("updating finished, duration: {} 毫秒", System.currentTimeMillis() - start);
    }

}
