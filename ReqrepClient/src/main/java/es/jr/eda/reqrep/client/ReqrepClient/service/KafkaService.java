package es.jr.eda.reqrep.client.ReqrepClient.service;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaService {
    @Autowired
    private ReplyingKafkaTemplate<String, Object, Object> template;
    @Value("${client.send-topics}")
    private String SEND_TOPICS;

    public Object kafkaRequestReply(Object request) throws Exception {
        ProducerRecord<String, Object> record = new ProducerRecord<>(SEND_TOPICS, request);
        RequestReplyFuture<String, Object, Object> replyFuture = template.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        return consumerRecord.value();
    }
}
