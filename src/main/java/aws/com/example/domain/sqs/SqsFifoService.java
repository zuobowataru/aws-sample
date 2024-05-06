package aws.com.example.domain.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import aws.com.example.domain.ec2.Ec2Info;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.awspring.cloud.core.region.RegionProvider;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SqsFifoService {

    @Value("${queue.url.fifo:N/A}")
    private String fifoQueueUrl;

    @Autowired
    private Ec2Info ec2Info;

    private AmazonSQS sqs;

    /** コンストラクタ */
    @Autowired
    public SqsFifoService(AWSCredentialsProvider awsCredentialsProvider,
            RegionProvider regionProvider) {

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);
        clientConfig.setConnectionTimeout(5 * 1000);

        this.sqs = AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withClientConfiguration(clientConfig)
                .withRegion(regionProvider.getRegion().getName())
                .build();
    }

    /** オブジェクトの送信
     * @throws JsonProcessingException */
    public void sendFifoQueue(){

        try {
            log.info("before send queue={}, message={}", fifoQueueUrl, ec2Info.toJson());

            // メッセージリクエスト設定
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(fifoQueueUrl)
                    .withMessageBody(ec2Info.toJson())
                    .withMessageGroupId("handsonMessageGroupId");

            // メッセージ送信
            sqs.sendMessage(sendMessageRequest);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
