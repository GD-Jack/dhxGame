package com.jack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class dhxGameApplicationTests {

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String formEmail;

    @Test
    void testMail() throws MessagingException {
        /*MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("标题"); // 标题
        // 内容, 第二个参数为true则以html方式发送, 否则以普通文本发送
        helper.setText("<h1 style='red'>内容</h1>", true);

        helper.setTo("505617850@qq.com"); // 收件人
        helper.setFrom(formEmail); // 发件人
        javaMailSender.send(message); // 发送*/
    }

}
