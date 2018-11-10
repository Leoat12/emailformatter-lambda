import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class SQSEventHandler implements RequestHandler<SQSEvent, Void> {

    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        return null;
    }

}
