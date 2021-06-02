package com.mimehoo.reader.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimehoo.reader.entity.Book;
import com.mimehoo.reader.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manage/book")
public class MBookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/manage/book");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getBookList(Integer page, Integer limit) {
        // page 默认为1
        if (page == null || page <= 0) {
            page = 1;
        }

        // limit 默认为 10
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        IPage<Book> pageInfo = bookService.paging(null, null, page, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("msg", "success");
        map.put("data", pageInfo.getRecords());//当前页面数据
        map.put("count", pageInfo.getTotal());//未分页时记录总数
        return map;

    }
}
