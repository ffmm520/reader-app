package com.mimehoo.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimehoo.reader.entity.Book;
import com.mimehoo.reader.entity.Category;
import com.mimehoo.reader.entity.Evaluation;
import com.mimehoo.reader.entity.Member;
import com.mimehoo.reader.service.BookService;
import com.mimehoo.reader.service.CategoryService;
import com.mimehoo.reader.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/")
    public ModelAndView showIndex(HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        ModelAndView mv = new ModelAndView("/index");
        List<Category> list = categoryService.findAll();
        mv.addObject("categoryList", list);
        mv.addObject("loginMember", loginMember);
        return mv;
    }


    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> findBooks(Long categoryId, String order,Integer p){
        if (p == null || p <= 0) {
            p = 1;
        }
        return bookService.paging(categoryId, order, p, 10);
    }

    @GetMapping("/book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long bookId){
        Book book = bookService.selectById(bookId);
        List<Evaluation> list = evaluationService.selectByBookId(bookId);
        ModelAndView mv = new ModelAndView("/detail");
        mv.addObject("book", book);
        mv.addObject("evaluationList", list);
        return mv;
    }
}
