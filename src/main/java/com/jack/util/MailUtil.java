package com.jack.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@PropertySource(value = "classpath:application.properties", encoding = "utf-8")
@Component
public class MailUtil {

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String formEmail;

    @Value("${spring.mail.formName}")
    private String formName;


    /**
     * 发送验证码格式格式邮件
     * @param toEmail                 接收者邮箱
     * @param toName                  接收者昵称
     * @param title                   邮箱标题
     * @param message                 操作
     * @param securityCodeUtil        验证码
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void sendTemplateMail(String toEmail, String toName, String title, String message, String securityCodeUtil){
        try {
            formName = new String(formName.getBytes("ISO-8859-1"),"UTF-8");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(new InternetAddress(formEmail, formName,"UTF-8"));

            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(title);

            Context context=new Context();

            context.setVariable("userName", toName);
            context.setVariable("message", message);
            context.setVariable("securityCodeUtil", securityCodeUtil);
            String content=templateEngine.process("htmlMail",context);


            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送Html格式邮件
     * @param toEmail 接收者
     * @param title     标题
     * @param html      正文
     */
    public void sendHtmlMail(String toEmail, String title, String html) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(formEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送普通邮件
     * @param toEmail 接收者
     * @param title     标题
     * @param text      正文
     */
    public void sendTextMail(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(text);
        message.setTo(formEmail);
        message.setFrom(toEmail);
        javaMailSender.send(message);
    }

}
