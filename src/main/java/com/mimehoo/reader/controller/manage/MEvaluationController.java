package com.mimehoo.reader.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manage/evaluation")
public class MEvaluationController {

    @GetMapping("index.html")
    public ModelAndView showIndex(){
        return new ModelAndView("/manage/evaluation");
    }
}
