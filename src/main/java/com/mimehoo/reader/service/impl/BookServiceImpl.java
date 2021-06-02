package com.mimehoo.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimehoo.reader.entity.Book;
import com.mimehoo.reader.entity.Evaluation;
import com.mimehoo.reader.entity.MemberReadState;
import com.mimehoo.reader.mapper.BookMapper;
import com.mimehoo.reader.mapper.EvaluationMapper;
import com.mimehoo.reader.mapper.MemberReadStateMapper;
import com.mimehoo.reader.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private MemberReadStateMapper memberReadStateMapper;

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

    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        // 删除书籍
        bookMapper.deleteById(bookId);

        // 删除评论
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);

        // 删除用户阅读状态
        QueryWrapper<MemberReadState> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.deleteById(bookId);
    }
}
