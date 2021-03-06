package com.zoey.controller;

import com.zoey.reps.CommonResp;
import com.zoey.reps.EbookStatsResp;
import com.zoey.service.EbookSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook-snapshot")
public class EbookStatsController {

    @Autowired
    private EbookSnapshotService ebookSnapshotService;

    @RequestMapping("get-statistic")
    public CommonResp getStatistic() {
        CommonResp<List<EbookStatsResp>> commonResp = new CommonResp<>();
        List<EbookStatsResp> ebookStatsResps = ebookSnapshotService.getStatistics();
        commonResp.setContent(ebookStatsResps);
        return commonResp;
    }

    @RequestMapping("get-30-statistic")
    public CommonResp get30Statistic() {
        CommonResp<List<EbookStatsResp>> commonResp = new CommonResp<>();
        List<EbookStatsResp> ebookStatsResps = ebookSnapshotService.get30Statistics();
        commonResp.setContent(ebookStatsResps);
        return commonResp;
    }
}
