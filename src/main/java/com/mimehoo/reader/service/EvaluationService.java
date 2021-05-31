package com.mimehoo.reader.service;

import com.mimehoo.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 根据bookId查询 评论
     * @param bookId bookId
     * @return 评论列表
     */
    List<Evaluation> selectByBookId(Long bookId);


    /**
     * 添加用户评论
     * @param bookId 图书id
     * @param memberId 用户id
     * @param score 评分
     * @param content 评价内容
     * @return 评论值
     */
    Evaluation evaluate(Long bookId, Long memberId, Integer score, String content);

    /**
     * 评论点赞
     * @param evaluateId 评论id
     * @return 评论
     */
    Evaluation enjoy(Long evaluateId);
}
