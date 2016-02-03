package net.liuxuan.springboottest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Moses on 2016/2/3.
 */

@RestController
//@Configuration
//@ComponentScan
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/{id}")
    //在方法的参数中加上@PathVariable("variableName")，
    // 那么当请求被转发给该方法处理时，
    // 对应的URL中的变量会被自动赋值给被@PathVariable注解的参数
    // （能够自动根据参数类型赋值，例如上例中的int）。
    public User view(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("zhang");
        return user;
    }
}
