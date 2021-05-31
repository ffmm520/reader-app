package com.mimehoo.reader.service;

import com.mimehoo.reader.entity.Member;
import com.mimehoo.reader.entity.MemberReadState;

public interface MemberService {
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 用户信息
     */
    Member register(String username, String password, String nickname);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    Member login(String username, String password);

    /**
     * 获取阅读状态
     * @param bookId 图书id
     * @param memberId 会员id
     * @return 阅读状态
     */
    MemberReadState getReadState(Long bookId, Long memberId);

    /**
     * 更新阅读状态
     * @param bookId 图书id
     * @param memberId 会员id
     * @param state 状态
     * @return 阅读状态
     */
    MemberReadState updateReadState(Long bookId, Long memberId, Integer state);
}
