package com.mimehoo.reader.controller;

import com.mimehoo.reader.entity.Evaluation;
import com.mimehoo.reader.entity.Member;
import com.mimehoo.reader.entity.MemberReadState;
import com.mimehoo.reader.exception.BusinessException;
import com.mimehoo.reader.service.EvaluationService;
import com.mimehoo.reader.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.lang.model.element.NestingKind;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/register.html")
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }


    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(String vc, String username, String password, String nickname, HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        Map<String, String> map = new HashMap<>();
        if (vc == null || !vc.equalsIgnoreCase(code)) {
            map.put("code", "VC01");
            map.put("msg", "验证码错误");
        } else {
            try {
                Member register = memberService.register(username, password, nickname);
                if (register != null) {
                    map.put("code", "0");
                    map.put("msg", "success");
                }
            } catch (BusinessException ex) {
                ex.printStackTrace();
                map.put("code", ex.getCode());
                map.put("msg", ex.getMsg());
            }
        }
        return map;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(String username, String password, String vc, HttpSession session) {
        String code = (String) session.getAttribute("kaptchaVerifyCode");
        Map<String, String> map = new HashMap<>();
        if (vc == null || !vc.equalsIgnoreCase(code)) {
            map.put("code", "VC01");
            map.put("msg", "验证码错误");
        } else {
            try {
                Member member = memberService.login(username, password);
                session.setAttribute("loginMember", member);
                map.put("code", "0");
                map.put("msg", "success");
            } catch (BusinessException e) {
                e.printStackTrace();
                map.put("code", e.getCode());
                map.put("msg", e.getMsg());
            }
        }
        return map;
    }


    /**
     * 更新阅读状态
     *
     * @param bookId   图书id
     * @param memberId 会员id
     * @param state    状态值
     * @return 响应代码
     */
    @PostMapping("/update_read_state")
    @ResponseBody
    public Map<String, String> updateReadState(Long bookId, Long memberId, Integer state) {
        Map<String, String> map = new HashMap<>();
        try {
            MemberReadState readState = memberService.updateReadState(bookId, memberId, state);
            map.put("code", "0");
            map.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("msg", e.getMsg());
        }
        return map;
    }

    /**
     * 添加用户评论
     *
     * @param bookId   图书id
     * @param memberId 用户id
     * @param score    评分
     * @param content  评价内容
     * @return 状态码
     */
    @PostMapping("/evaluate")
    @ResponseBody
    public Map<String, Object> evaluate(Long bookId, Long memberId, Integer score, String content) {
        Evaluation evaluate = null;
        Map<String, Object> map = new HashMap<>();
        try {
            evaluate = evaluationService.evaluate(bookId, memberId, score, content);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("evaluation", evaluate);
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("msg", e.getMsg());
        }
        return map;
    }

    @PostMapping("/enjoy")
    @ResponseBody
    public Map<String, Object> enjoy(Long evaluationId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Evaluation evaluation =  evaluationService.enjoy(evaluationId);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("evaluation", evaluation.getEnjoy());
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("msg", e.getMsg());
        }
        return map;
    }
}
