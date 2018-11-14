import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SQSEventHandler implements RequestHandler<SQSEvent, Void> {

    private static final Logger logger = LogManager.getLogger(SQSEventHandler.class);

    public Void handleRequest(SQSEvent sqsEvent, Context context) {

        logger.info("Initializing Email Formatter...");
        SendEmailService sendEmailService = new SendEmailService();
        SQSClient sqsClient = SQSClient.getInstance();

        sqsEvent.getRecords().forEach(m -> {
            logger.info("Processing SQS Message with ID: " + m.getMessageId());
            String messageId = sendEmailService.sendEmailFromSQS(m);
            logger.info("Email sent by SES, message id: " + messageId);
            sqsClient.removeFromQueue(m);
        });

        return null;
    }

}
