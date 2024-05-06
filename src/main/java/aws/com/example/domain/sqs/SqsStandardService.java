package aws.com.example.domain.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import aws.com.example.domain.ec2.Ec2Info;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SqsStandardService {

    private QueueMessagingTemplate messagingTemplate;

    @Value("${queue.name.standard:N/A}")
    private String standardQueueName;

    @Value("${queue.name.dead:N/A}")
    private String deadLetterQueueName;

    @Autowired
    private Ec2Info ec2Info;

    /** コンストラクタ */
    @Autowired
    public SqsStandardService(AmazonSQSAsync amazonSQSAsync) {
        this.messagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    /** 文字列の送信 */
    public void sendStandard(String message) {
        log.info("before send queue={}, message={}", standardQueueName, message);
        
        // ペイロード作成
        Message<String> payload = MessageBuilder.withPayload(message).build();
        // メッセージ送信
        this.messagingTemplate.send(standardQueueName, payload);
    }

    /** オブジェクトの送信 */
    public void sendDeadLetterQueue() {
        log.info("before send queue={}, message={}", deadLetterQueueName, ec2Info);
        
        // ペイロード作成
        Message<Ec2Info> payload = MessageBuilder.withPayload(ec2Info).build();
        // メッセージ送信
        this.messagingTemplate.send(deadLetterQueueName, payload);
    }
}
