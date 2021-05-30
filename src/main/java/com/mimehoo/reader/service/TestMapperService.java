package com.mimehoo.reader.service;

import com.mimehoo.reader.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestMapperService {
    @Resource
    private TestMapper testMapper;

    @Transactional
    public void batchImport(){
        for (int i = 0; i < 5; i++) {
            testMapper.insertSample();
            if (3== i) {
                throw new RuntimeException("程序抛锚辣");
            }
        }
    }
}
