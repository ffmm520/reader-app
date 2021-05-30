package com.mimehoo.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/test/ftl")
    public ModelAndView test1(){
        return new ModelAndView("/test");
    }

    @RequestMapping("/test2/json")
    @ResponseBody
    public Map<String, String> test2(){
        Map<String, String> map = new HashMap<>();
        map.put("hello", "freemarker");
        return map;
    }
}
