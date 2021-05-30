package com.mimehoo.reader.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimehoo.reader.mapper.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    public void testInsert() {
        com.mimehoo.reader.entity.Test test = new com.mimehoo.reader.entity.Test();
        test.setContent("Mybatis-Plus content： int");
        int i = testMapper.insert(test);
        System.out.println("影响的行数是：" + i);
    }

    @Test
    public void testUpdate() {
        com.mimehoo.reader.entity.Test test = testMapper.selectById(16);
        test.setContent("this is from testUpdate method");
        int i = testMapper.updateById(test);
        System.out.println("update count: " + i);
    }

    @Test
    public void testSelect() {
        QueryWrapper<com.mimehoo.reader.entity.Test> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 17);
        wrapper.or();//或者， 拼接条件前后紧跟
        wrapper.lt("id", 7);
        // List<com.mimehoo.reader.entity.Test> list = testMapper.selectList(wrapper);
        // 查询一个
        // com.mimehoo.reader.entity.Test test = testMapper.selectOne(wrapper);

        // 查询多个
        List<com.mimehoo.reader.entity.Test> list = testMapper.selectList(wrapper);
        System.out.println(list);
    }
}
