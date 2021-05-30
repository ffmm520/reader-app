package com.mimehoo.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimehoo.reader.entity.Evaluation;
import com.mimehoo.reader.entity.Member;
import com.mimehoo.reader.mapper.EvaluationMapper;
import com.mimehoo.reader.mapper.MemberMapper;
import com.mimehoo.reader.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private MemberMapper memberMapper;
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id", bookId);
        wrapper.orderByDesc("create_time");
        List<Evaluation> list = evaluationMapper.selectList(wrapper);
        for (Evaluation evaluation : list) {
            Member member = memberMapper.selectById(evaluation.getMemberId());
            evaluation.setMember(member);
        }
        return list;
    }
}
