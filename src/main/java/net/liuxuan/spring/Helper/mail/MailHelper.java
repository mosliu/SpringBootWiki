package net.liuxuan.spring.Helper.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.PathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.mail.MailgunHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/27 11:09
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/27  |    Moses       |     Created
 */
@Component
@ConfigurationProperties(prefix = "mail.smtps")
public class MailHelper {

    public static String EMAIL_TEMPLATE_ENCODING = "UTF-8";
    private static JavaMailSender mailSender;
    private static TemplateEngine emailTemplateEngine;
    private static String username;
    private String host;
    private boolean auth;
    private String password;
    private int port;

    @Autowired
    private ApplicationContext appContext;

//    @Autowired
//    public MailgunHelper(@Qualifier(value = "mailSender2")JavaMailSender mailSender,@Qualifier(value = "emailTemplateEngine")TemplateEngine emailTemplateEngine) {
//        this.mailSender = mailSender;
////        MailgunHelper.mailSender = mailSender();
//        MailgunHelper.emailTemplateEngine =emailTemplateEngine;
//    }

    /**
     * 发送邮件函数
     *
     * @param to        收件人
     * @param subject   邮件主题
     * @param variables 变量Map<String, Object>，如果需要突破，需将图片构造成List<Map<String,String>> imageList = new ArrayList<>();的形式，
     *                  List名称定义为 "imageList"
     *                  每个图片Map包含3项，"imageResourceName"：图片名，供在模板中调用， "imagePath"，图片路径 "imageContentType"：突破类型
     * @param template  使用的模板名
     * @throws MessagingException
     */
    public static void SendMail(String to, String subject, Map<String, Object> variables, String template) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // true = multipart
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, EMAIL_TEMPLATE_ENCODING);
        message.setSubject(subject);
        message.setFrom(username);
        message.setTo(to);


        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariables(variables);
//
//        List<Map<String, String>> imageList = (List<Map<String, String>>) variables.get("imageList");
//        if (imageList != null) {
////            存在ImageList
//            imageList.forEach(image -> {
//                if (image.get("imageResourceName") != null) {
//                    ctx.setVariable(image.get("imageResourceName"), image.get("imageResourceName"));
//                    InputStreamSource imageSource = new PathResource(image.get("imagePath"));
//                    try {
//                        message.addInline(image.get("imageResourceName"), imageSource, image.get("imageContentType"));
//                    } catch (MessagingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }

        // Create the HTML body using Thymeleaf
//        template = "mail/mailTicketSubmit";

        List<Map<String, String>> imageList = (List<Map<String, String>>) variables.get("imageList");
        if (imageList != null) {
//            存在ImageList
            imageList.forEach(image -> {
                if (image.get("imageResourceName") != null) {
                    ctx.setVariable(image.get("imageResourceName"), image.get("imageResourceName"));
                }
            });
        }

        final String htmlContent = emailTemplateEngine.process(template, ctx);
        message.setText(htmlContent, true); // true = isHtml

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"

//        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
//        final InputStreamSource imageSource = new PathResource("static/officiallogo.png");
//        String imageContentType = "image/png";
//        message.addInline(imageResourceName, imageSource, imageContentType);
//        List<Map<String, String>> imageList = (List<Map<String, String>>) variables.get("imageList");
        if (imageList != null) {
//            存在ImageList
            imageList.forEach(image -> {
                if (image.get("imageResourceName") != null) {
//                    ctx.setVariable(image.get("imageResourceName"), image.get("imageResourceName"));
                    InputStreamSource imageSource = new PathResource(image.get("imagePath"));
                    try {
                        message.addInline(image.get("imageResourceName"), imageSource, image.get("imageContentType"));
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // Send mail
        mailSender.send(mimeMessage);

    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MailHelper.username = username;
    }

    @PostConstruct
    public void init() {
        this.mailSender = mailSender();
        this.emailTemplateEngine = emailTemplateEngine();
    }

    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setHost(host);
        mailSender.setPort(port);
        return mailSender;
    }

//    @Bean(name = "templateMessage")
//    public SimpleMailMessage templateMessage() {
//        SimpleMailMessage templateMessage = new SimpleMailMessage();
//        templateMessage.setFrom("postmaster@sandbox6e03981472514c8ea3c3100c85097cd2.mailgun.org");
//        templateMessage.setSubject("public domain is your das");
//        return templateMessage;
//    }

    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
//        templateEngine.addTemplateResolver(textTemplateResolver());
        // Resolver for HTML emails (except the editable one)
//        templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Resolver for HTML editable emails (which will be treated as a String)
//        templateEngine.addTemplateResolver(stringTemplateResolver());
        templateEngine.addTemplateResolver(springResourceTemplateResolver());
        // Message source, internationalization specific to emails
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }


//    public ClassLoaderTemplateResolver classLoaderTemplateResolver() {
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//
//        templateResolver.setPrefix("mail/");
////        templateResolver.setPrefix("classpath:/templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML");
//        templateResolver.setCharacterEncoding("UTF-8");
//        templateResolver.setCacheable(true);
//        templateResolver.setOrder(3);
//        return templateResolver;
//    }
//
//    private ITemplateResolver textTemplateResolver() {
//        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(1));
//        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
////        templateResolver.setPrefix("mail/");
//        templateResolver.setPrefix("classpath:/mail/");
//        templateResolver.setSuffix(".txt");
//        templateResolver.setTemplateMode(TemplateMode.TEXT);
//        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
//
//    private ITemplateResolver htmlTemplateResolver() {
//        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(2));
////        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
//        templateResolver.setPrefix("classpath:/mail/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
//
//    private ITemplateResolver stringTemplateResolver() {
//        final StringTemplateResolver templateResolver = new StringTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(3));
//        // No resolvable pattern, will simply process as a String template everything not previously matched
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }

    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(appContext);
        templateResolver.setPrefix("classpath:/templates/");
//        templateResolver.setPrefix("classpath:/mail/");
//        templateResolver.setPrefix("mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(true);
        templateResolver.setOrder(1);
        return templateResolver;
    }

    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource. setBasename("mail/MailMessages");
        return messageSource;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
