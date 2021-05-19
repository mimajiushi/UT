package run.ut.app.mail;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import run.ut.app.event.options.OptionsUpdatedEvent;
import run.ut.app.service.OptionsService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Mail service implementation.
 *
 * @author ryanwang
 * @author johnniang
 * @author wenjie
 * @date 2020-4-6
 */
@Slf4j
@Service
public class MailServiceImpl extends AbstractMailService implements ApplicationListener<OptionsUpdatedEvent> {

    private final FreeMarkerConfigurer freeMarker;

    public MailServiceImpl(FreeMarkerConfigurer freeMarker,
                           OptionsService optionsService) {
        super(optionsService);
        this.freeMarker = freeMarker;
    }

    @Override
    public void sendTextMail(String to, String subject, String content) {
        sendMailTemplate(true, messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(content);
        });
    }

    @Override
    public void sendTemplateMail(String to, String subject, Map<String, Object> content, String templateName) {
        sendMailTemplate(true, messageHelper -> {
            // build message content with freemarker
            Template template = freeMarker.getConfiguration().getTemplate(templateName);
            String contentResult = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);

            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(contentResult, true);
        });
    }

    @Override
    public void sendAttachMail(String to, String subject, Map<String, Object> content, String templateName, String attachFilePath) {
        sendMailTemplate(true, messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            Path attachmentPath = Paths.get(attachFilePath);
            messageHelper.addAttachment(attachmentPath.getFileName().toString(), attachmentPath.toFile());
        });
    }

    @Override
    public void testConnection() {
        super.testConnection();
    }


    @Override
    public void onApplicationEvent(OptionsUpdatedEvent event) {
        // clear the cached java run.ut.mail sender
        clearCache();
    }
}
