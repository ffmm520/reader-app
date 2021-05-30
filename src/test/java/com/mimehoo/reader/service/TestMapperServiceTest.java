package com.mimehoo.reader.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMapperServiceTest extends TestCase {

    @Autowired
    private TestMapperService testMapperService;

    @Test
    public void testBatchImport() {
        testMapperService.batchImport();
        System.out.println("批量导入成功");
    }
}