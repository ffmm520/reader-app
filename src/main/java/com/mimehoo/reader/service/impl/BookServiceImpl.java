package com.mimehoo.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimehoo.reader.entity.Book;
import com.mimehoo.reader.mapper.BookMapper;
import com.mimehoo.reader.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public IPage<Book> paging(Long categoryId, String order,Integer pageNum, Integer pageSize) {
        // 可以通过构造方法直接传递分页参数
        // Page<Book> page = new Page<>(pageNum, pageSize);
        Page<Book> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        // 构造查询条件
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if (categoryId != null && categoryId != -1) {
            wrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            // 根据评分排序
            if ("score".equals(order)) {
                wrapper.orderByDesc("evaluation_score");
            }

            // 根据热度排序
            if ("quantity".equals(order)) {
                wrapper.orderByDesc("evaluation_quantity");
            }
        }
        Page<Book> bookPage = bookMapper.selectPage(page, wrapper);
        return bookPage;
    }

    @Override
    public Book selectById(Long bookId) {
        return bookMapper.selectById(bookId);
    }
}
