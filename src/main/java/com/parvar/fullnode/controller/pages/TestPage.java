package com.parvar.fullnode.controller.pages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/10/11 15:35
 * @description：演示页面与模板的使用
 * @modified By：
 */
@Controller
public class TestPage extends PageBase {
    @Value("${spring.mail.password}")
    private String emPassword; // 数据库密码
    @GetMapping(value = "/index.htm")
    public String Index(Model model){
        model.addAttribute("username",emPassword);
        return getTemPath("index");
    }
}
