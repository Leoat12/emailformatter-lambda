import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SendEmailService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String TEMPLATE = "aws-leoat12-bucket/template1.html";

    private Configuration freeMarkerConfiguration(){
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setLocalizedLookup(false);
        cfg.setTemplateLoader(new S3URLTemplateLoader());
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private String processTemplateIntoString(Template template, Object model) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    private String buildEmailTemplate(Map<String, Object> data){

        Configuration configuration = freeMarkerConfiguration();

        try {
            Template template = configuration.getTemplate(TEMPLATE);
            return processTemplateIntoString(template, data);
        } catch (Exception e){
            return null;
        }

    }

    public String sendEmailFromLocal(Map<String, Object> data){
        String content = buildEmailTemplate(data);

        String source = data.get("email.source").toString();
        String destination = data.get("email.destination").toString();
        String subject = data.get("email.subject").toString();

        SESClient sesClient = SESClient.getInstance();
        return sesClient.sendEmail(source, destination, subject, content);
    }

    public String sendEmailFromSQS(SQSEvent.SQSMessage message){
        try {
            JavaType mapType = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
            Map<String, Object> data = objectMapper.readValue(message.getBody(), mapType);

            String content = buildEmailTemplate(data);
            String source = data.get("email.source").toString();
            String destination = data.get("email.destination").toString();
            String subject = data.get("email.subject").toString();

            SESClient sesClient = SESClient.getInstance();
            return sesClient.sendEmail(source, destination, subject, content);

        } catch (Exception e) {
            return null;
        }
    }
}
