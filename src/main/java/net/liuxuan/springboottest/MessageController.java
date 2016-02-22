/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.springboottest;

import net.liuxuan.springboottest.message.Message;
import net.liuxuan.springboottest.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by Moses on 2016/2/14.
 */

@Controller
public class MessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping("/msg")
//    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    public ModelAndView list() {
        Iterable<Message> messages = this.messageRepository.findAll();
        return new ModelAndView("messages/list", "messages", messages);
    }

    @RequestMapping("/msg/{id}")
    public ModelAndView view(@PathVariable("id") Message message) {
        return new ModelAndView("messages/view", "message", message);
    }

                //@ModelAttribute
    //在方法定义上使用 @ModelAttribute 注解：Spring MVC 在调用目标处理方法前，会先逐个调用在方法级上标注了@ModelAttribute 的方法
    //　　在方法的入参前使用 @ModelAttribute 注解：可以从隐含对象中获取隐含的模型数据中获取对象，再将请求参数 –绑定到对象中，再传入入参将方法入参对象添加到模型中*/

    @RequestMapping(value = "/msg", params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Message message) {
        return "messages/form";
    }

    //@Valid：对实体类的一个验证。验证符合jpa的标准。要和BindingResult result配合使用，如果验证不通过的话，result.hasErrors()，跳转 。如一个实体类标准：
    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public ModelAndView create(@Valid Message message, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("messages/form", "formErrors", result.getAllErrors());
        }
        message = this.messageRepository.save(message);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
        return new ModelAndView("redirect:msg/{message.id}", "message.id", message.getId());
    }

//    @RequestMapping("foo")
//    public String foo() {
//        throw new RuntimeException("Expected exception in controller");
//    }

    //    @PathVariable：rest访问方式获取参数传递
    @RequestMapping(value = "/msg/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.messageRepository.deleteMessage(id);
        Iterable<Message> messages = this.messageRepository.findAll();
        return new ModelAndView("messages/list", "messages", messages);
    }

    @RequestMapping(value = "/msg/modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") Message message) {
        return new ModelAndView("messages/form", "message", message);
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


}
