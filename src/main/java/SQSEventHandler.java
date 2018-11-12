import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class SQSEventHandler implements RequestHandler<SQSEvent, Void> {

    public Void handleRequest(SQSEvent sqsEvent, Context context) {

        LambdaLogger logger = context.getLogger();

        logger.log("Initializing Email Formatter...");
        SendEmailService sendEmailService = new SendEmailService(logger);

        sqsEvent.getRecords().forEach(m -> {
            logger.log("Processing SQS Message with ID: " + m.getMessageId());
            String messageId = sendEmailService.sendEmailFromSQS(m);
            logger.log("Email sent by SES, message id: " + messageId);
        });

        return null;
    }

}
