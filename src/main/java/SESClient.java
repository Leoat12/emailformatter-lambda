import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class SESClient {

    private static final Logger logger = LogManager.getLogger(SESClient.class);

    private static SESClient INSTANCE;
    private AmazonSimpleEmailService client;

    private SESClient(){
        client = AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1).build();
    }

    public static SESClient getInstance(){
        logger.info("Getting SES Instance...");
        if(Objects.isNull(INSTANCE)) {
            logger.info("Instance not found...");
            INSTANCE = new SESClient();
            return INSTANCE;
        }
        else {
            logger.info("Instance found!");
            return INSTANCE;
        }
    }

    public String sendEmail(String from, String to, String subject, String body){
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(body)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(subject)))
                .withSource(from);

        try {
            SendEmailResult result = client.sendEmail(request);
            return result.getMessageId();
        } catch (Exception e){
            logger.error(e);
            return null;
        }
    }
}
