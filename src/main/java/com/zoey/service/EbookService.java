package com.zoey.service;

import com.zoey.domain.Ebook;
import com.zoey.mapper.EbookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbookService {

    @Autowired
    private EbookMapper ebookMapper;

    public List<Ebook> getList() {
        return ebookMapper.selectByExample(null);
        //return ebookMapper.selectByPrimaryKey();
    }

    public Ebook getEbookById(long id) {
        // return ebookMapper.selectByExample(null);
        return ebookMapper.selectByPrimaryKey(id);
    }
}
