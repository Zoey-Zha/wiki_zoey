package com.zoey.service;

import com.zoey.domain.Test;
import com.zoey.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Test> getList() {
        return testMapper.getList();
    }
}
