package com.mimehoo.reader.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimehoo.reader.entity.Book;
import com.mimehoo.reader.service.BookService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> addBook(@RequestParam("img") MultipartFile img, HttpServletRequest request) throws IOException {
        // 获取文件上传路径
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        // 获取文件后缀名
        String filename = img.getOriginalFilename();
        int i = 0;
        try {
            assert filename != null;
            i = filename.lastIndexOf(".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // .jpg
        String suffix = filename.substring(i);
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String newFile = newFileName+suffix;
        img.transferTo(new File(uploadPath + newFile));
        Map<String, Object> map = new HashMap<>();
        map.put("errno", "0");
        map.put("data",new String[]{"/upload/"+newFile});
        return map;
    }

    @PostMapping("create")
    @ResponseBody
    public Map<String, String> createBook(Book book){
        Map<String, String> map = new HashMap<>();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0F);
            Document doc = Jsoup.parse(book.getDescription());
            Elements img = doc.select("img");
            String src = img.attr("src");
            book.setCover(src);
            bookService.createBook(book);
            map.put("code", "0");
            map.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "error");
        }
        return map;
    }
}
