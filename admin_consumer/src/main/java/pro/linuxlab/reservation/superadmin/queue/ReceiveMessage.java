package pro.linuxlab.reservation.superadmin.queue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public interface ReceiveMessage {
    @KafkaListener(
            topics  = "${spring.kafka.template.site.topic}",
            groupId = "${spring.kafka.consumer.site.group-id}"
    )
    void siteListener(ConsumerRecord<String, String> consumerRecord);
    @KafkaListener(
            topics  = "${spring.kafka.template.user.topic}",
            groupId = "${spring.kafka.consumer.user.group-id}"
    )
    void userListener(ConsumerRecord<String, String> consumerRecord);
}
