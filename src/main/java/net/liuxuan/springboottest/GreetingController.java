package net.liuxuan.springboottest;

import net.liuxuan.spring.constants.DateFormatConstants;
import net.liuxuan.spring.helper.SystemHelper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Moses on 2016/2/3.
 */

@Controller
public class GreetingController {
//    @RequestMapping(method=GET)


    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @RequestMapping("/foo1")
    public String foo1(HttpServletRequest request, HttpServletResponse response) throws NoHandlerFoundException {

        throw new NoHandlerFoundException(request.getMethod(),request.getRequestURL().toString(),new HttpHeaders());
    }

    @ResponseBody
    @RequestMapping("/foo2")
    public String foo2(HttpServletRequest request, HttpServletResponse response) throws NoHandlerFoundException {
//        String a = "foo2";
        return SystemHelper.getCurrentUserIp();

    }


    /**
     * 将form表单里面的String Date转换成Date型，字符串去掉空白
     */
    @InitBinder
    protected final void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(DateFormatConstants.YYYY_MM_DD), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
