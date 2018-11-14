import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class SQSClient {

    private static final Logger logger = LogManager.getLogger(SQSClient.class);

    private static SQSClient INSTANCE;
    private AmazonSQS client;

    private SQSClient(){
        client = AmazonSQSClientBuilder.standard().build();
    }

    public static SQSClient getInstance(){
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new SQSClient();
            return INSTANCE;
        } else {
            return INSTANCE;
        }
    }

    public boolean removeFromQueue(SQSEvent.SQSMessage message){

        try {
            String queueUrl = System.getenv("SQS_QUEUE");
            logger.info("Removing message with Receipt Handle: {}", message.getReceiptHandle());
            client.deleteMessage(queueUrl, message.getReceiptHandle());

            return true;
        } catch (Exception e){
            logger.error(e);
            return false;
        }
    }
}
