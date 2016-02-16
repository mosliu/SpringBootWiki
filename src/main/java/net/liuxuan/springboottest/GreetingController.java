package net.liuxuan.springboottest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
 * Created by Moses on 2016/2/3.
 */

@Controller
public class GreetingController {
//    @RequestMapping(method=GET)
@RequestMapping("/")
public String home(Map<String, Object> model) {
    model.put("message", "Hello World");
    model.put("title", "Hello Home");
    model.put("date", new Date());
    return "home";
}

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

}
