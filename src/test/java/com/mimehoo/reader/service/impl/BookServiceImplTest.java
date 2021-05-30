package com.mimehoo.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimehoo.reader.entity.Book;
import com.mimehoo.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Test
    public void paging() {
        IPage<Book> result = bookService.paging(1L, "score", 1, 5);
        List<Book> records = result.getRecords();
        System.out.println("一共有：" + result.getTotal());
        for (Book record : records) {
            System.out.println(record);
        }

    }
}