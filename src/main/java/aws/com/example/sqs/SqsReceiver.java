package aws.com.example.sqs;

import org.springframework.stereotype.Service;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SqsReceiver {
    
    /** Standardキューリスナー */
    @SqsListener("Handson-Standard-Queue")
    public void standardQueueListener(String message) {
        log.info("standardQueue, message = {}", message);
    }
    
    /** DeadLetterキューリスナー 
     * @throws Exception */
    @SqsListener("Handson-DeadLetter-Queue")
    public void deadLetterQueueListener(String message) throws Exception {
        log.info("deadLetterQueue, message = {}", message);
        throw new Exception("DeadLetterTest");
    }
    
    /** FIFOキューリスナー */
    @SqsListener("Handson-FIFO-Queue.fifo")
    public void fifoQueueListener(String message) {
        log.info("fifoLetterQueue, message = {}", message);
    }
}
