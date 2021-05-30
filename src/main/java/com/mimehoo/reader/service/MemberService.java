package com.mimehoo.reader.service;

import com.mimehoo.reader.entity.Member;

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
}
