import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.cache.ClassTemplateLoader;
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
    private final String TEMPLATE = "template1.html";

    private Configuration freeMarkerConfiguration(){
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "/templates"));
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private String processTemplateIntoString(Template template, Object model) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    public String buildEmailTemplate(Map<String, Object> data){

        Configuration configuration = freeMarkerConfiguration();

        try {
            Template template = configuration.getTemplate(TEMPLATE);
            return processTemplateIntoString(template, data);
        } catch (Exception e){
            return null;
        }

    }

    public void sendEmailFromSQS(SQSEvent.SQSMessage message){
        try {
            JavaType mapType = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
            Map<String, Object> data = objectMapper.readValue(message.getBody(), mapType);
            String content = buildEmailTemplate(data);
        } catch (Exception e) {
            return;
        }
    }
}
