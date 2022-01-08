package com.zoey.service;

import com.zoey.domain.Demo;
import com.zoey.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    public List<Demo> getList() {
        return demoMapper.selectByExample(null);
        //return demoMapper.selectByPrimaryKey();
    }

    public Demo getDemoById(long id) {
        // return demoMapper.selectByExample(null);
        return demoMapper.selectByPrimaryKey(id);
    }
}
