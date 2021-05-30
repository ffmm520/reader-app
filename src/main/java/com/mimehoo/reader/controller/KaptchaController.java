package com.mimehoo.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

// 验证码控制器
@Controller
public class KaptchaController {
    @Autowired
    private Producer kaptchaProducer;

    @GetMapping("/verifyCode")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 响应立即过期
        response.setDateHeader("Expire", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-chek=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        // 验证码字符
        String text = kaptchaProducer.createText();
        HttpSession session = request.getSession();
        session.setAttribute("kaptchaVerifyCode",text);
        System.out.println(session.getAttribute("kaptchaVerifyCode"));
        // 创建验证码图片
        BufferedImage image = kaptchaProducer.createImage(text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"png", out);
        out.flush();
        out.close();
    }
}
