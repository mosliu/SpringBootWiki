package net.liuxuan.springboottest;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Moses on 2016/2/3.
 */

@Controller
@RequestMapping("/hello/")
public class dbController {
    @RequestMapping("/{name}")
    public String hello(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        //返回值"hello"并非直接将字符串返回给浏览器，而是寻找名字为hello的模板进行渲染
        return "hello";
    }
}
