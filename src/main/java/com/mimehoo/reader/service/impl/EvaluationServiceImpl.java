package com.mimehoo.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimehoo.reader.entity.Evaluation;
import com.mimehoo.reader.entity.Member;
import com.mimehoo.reader.mapper.EvaluationMapper;
import com.mimehoo.reader.mapper.MemberMapper;
import com.mimehoo.reader.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public Evaluation evaluate(Long bookId, Long memberId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setEnjoy(0);
        evaluation.setState("enable");
        evaluation.setMemberId(memberId);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    @Override
    public Evaluation enjoy(Long evaluateId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluateId);
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }
}
