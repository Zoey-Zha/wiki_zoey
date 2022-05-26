package com.zoey.mapper;

import com.zoey.reps.EbookStatsResp;

import java.util.List;

public interface EbookSnapshotMapperCust {

    public void getEbookSnapshot();

    public List<EbookStatsResp> getEbookStats();

    public List<EbookStatsResp> get30EbookStats();
}