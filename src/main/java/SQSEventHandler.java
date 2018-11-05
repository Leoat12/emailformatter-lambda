import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import java.util.Collections;

public class SQSEventHandler implements RequestHandler<SQSEvent, Void> {

    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new SendEmailService().buildEmailTemplate(Collections.singletonMap("USERNAME", "Leonardo")));
    }
}
