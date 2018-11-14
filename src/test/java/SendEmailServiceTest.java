import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class SendEmailServiceTest {

//    @Test
//    public void testEmailSending() throws IOException {
//        SendEmailService sendEmailService = new SendEmailService();
//
//        Properties props = new Properties();
//        props.load(SendEmailServiceTest.class.getClassLoader().getResourceAsStream("function.properties"));
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("USERNAME", "Leonardo");
//        data.put("email.source", props.getProperty("email.source"));
//        data.put("email.destination", props.getProperty("email.destination"));
//        data.put("email.subject", props.getProperty("email.subject"));
//        data.put("s3.bucketName", props.getProperty("s3.bucketName"));
//        data.put("s3.filePath", props.getProperty("s3.filePath"));
//
//        String messageId = sendEmailService.sendEmailFromLocal(data);
//
//        assertThat(messageId, not(equalTo(null)));
//    }


}
