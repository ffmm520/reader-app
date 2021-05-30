package com.mimehoo.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimehoo.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    void insertSample();
}
