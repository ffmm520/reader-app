package com.mimehoo.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimehoo.reader.entity.Member;
import com.mimehoo.reader.exception.BusinessException;
import com.mimehoo.reader.mapper.MemberMapper;
import com.mimehoo.reader.service.MemberService;
import com.mimehoo.reader.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member register(String username, String password, String nickname) {
        Member m = new Member();
        if (username != null && password != null && nickname != null) {
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            Member member = memberMapper.selectOne(wrapper);
            if (member != null) {
                // 用户名重复
                throw new BusinessException("M01", "该用户名已存在");
            }
            m.setUsername(username);
            m.setNickName(nickname);
            // 密码加盐
            // 1. 生成随机4位数盐值
            int salt = new Random().nextInt(1000) + 1000;
            // 2. 加密原始密码
            String s = MD5Util.md5Digest(password, salt);
            // 3. 加密后的密码存入数据库
            m.setPassword(s);
            // 4. 盐存入数据库
            m.setSalt(salt);
            m.setCreateTime(new Date());
            memberMapper.insert(m);
        } else {
            throw new BusinessException("M01", "请输入正确的注册信息");
        }
        return m;
    }

    @Override
    public Member login(String username, String password) {
        Member member = null;
        if (username != null && password != null) {
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            member = memberMapper.selectOne(wrapper);
            if (member == null) {
                throw new BusinessException("M02", "请输入正确的用户名");
            }
            String target = MD5Util.md5Digest(password, member.getSalt());
            if (!target.equals(member.getPassword())) {
                // 用户输入的密码和数据库中的不匹配
                throw new BusinessException("M03", "请输入正确的密码");
            }
        } else {
            throw new BusinessException("M03", "请输入正确的用户名和密码");
        }
        return member;
    }
}
