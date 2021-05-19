package run.ut.app.mail;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Properties;

/**
 * Java run.ut.mail sender factory.
 *
 * @author johnniang
 * @author wenjie
 */
public class MailSenderFactory {

    /**
     * Get run.ut.mail sender.
     *
     * @param mailProperties run.ut.mail properties must not be null
     * @return java run.ut.mail sender
     */
    @NonNull
    public JavaMailSender getMailSender(@NonNull MailProperties mailProperties) {
        Assert.notNull(mailProperties, "Mail properties must not be null");

        // create run.ut.mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // set properties
        setProperties(mailSender, mailProperties);

        return mailSender;
    }

    private void setProperties(@NonNull JavaMailSenderImpl mailSender, @NonNull MailProperties mailProperties) {
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
        mailSender.setProtocol(mailProperties.getProtocol());
        mailSender.setDefaultEncoding(mailProperties.getDefaultEncoding().name());

        if (!CollectionUtils.isEmpty(mailProperties.getProperties())) {
            Properties properties = new Properties();
            properties.putAll(mailProperties.getProperties());
            mailSender.setJavaMailProperties(properties);
        }
    }
}
