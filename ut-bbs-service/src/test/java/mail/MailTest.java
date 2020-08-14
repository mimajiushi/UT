package mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.ut.app.UtBbsServiceApplication;
import run.ut.app.mail.MailService;

import java.util.HashMap;
import java.util.Map;

/**
 * Test send mail
 *
 * Please check your network, if the mail fails to be sent
 *
 * @author wenjie
 * @date 2020-4-6
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtBbsServiceApplication.class)
@Slf4j
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendTextMailTest() throws Exception {
        mailService.sendTextMail("1498780478@qq.com", "测试邮件", "测试内容");
        Thread.sleep(50000);
    }

    @Test
    public void sendTemplateMailTest() throws Exception{
        Map<String, Object> data = new HashMap<>();
        data.put("nickname", "chenwenjie");
        data.put("code", "123456");

        String subject = "UT管理员登录验证";
        String template = "mail_template/mail_login.ftl";
        mailService.sendTemplateMail("ut_test@163.com", subject, data, template);
        Thread.sleep(50000);
    }
}
