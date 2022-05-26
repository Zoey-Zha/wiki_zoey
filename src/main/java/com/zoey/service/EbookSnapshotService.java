package com.zoey.service;

import com.zoey.mapper.EbookSnapshotMapperCust;
import com.zoey.reps.EbookStatsResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookSnapshotService {

    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void genSnapShot() {
        ebookSnapshotMapperCust.getEbookSnapshot();
    }

    /**
     * 获取首页数值数据：阅读总数，点赞总数，今日阅读总数，今日点赞总数
     * @return
     */
    public List<EbookStatsResp> getStatistics() {
        return ebookSnapshotMapperCust.getEbookStats();
    }

}
