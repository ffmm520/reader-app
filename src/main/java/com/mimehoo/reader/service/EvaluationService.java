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
}
