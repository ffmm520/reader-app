package com.mimehoo.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimehoo.reader.entity.Book;

public interface BookService {
    /**
     * 分页查询
     * @param categoryId 分类id
     * @param order 排序类型
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     */
    IPage<Book> paging(Long categoryId, String order, Integer pageNum, Integer pageSize);

    /**
     * 根据bookId查询book
     * @param bookId bookId
     * @return book对象
     */
    Book selectById(Long bookId);

    Book createBook(Book book);
}
