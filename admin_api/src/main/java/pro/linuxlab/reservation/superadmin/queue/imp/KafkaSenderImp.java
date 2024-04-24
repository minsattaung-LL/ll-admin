package pro.linuxlab.reservation.superadmin.queue.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.config.AppConfig;
import pro.linuxlab.reservation.superadmin.queue.KafkaSender;

@Component
@Slf4j
public class KafkaSenderImp implements KafkaSender {

    final KafkaTemplate<String, String> kafkaTemplate;

    final AppConfig appConfig;

    public KafkaSenderImp(KafkaTemplate<String, String> kafkaTemplate, AppConfig appConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.appConfig = appConfig;
    }

    @Override
    public void sendSiteMessage(String message) {
        log.info("Sending message: [{}], Topic: [{}]", message, appConfig.getSiteTopic());
        log.info("----------------------------------");
        kafkaTemplate.send(appConfig.getSiteTopic(), message);
    }

    @Override
    public void sendUserMessage(String message) {
        log.info("Sending message: [{}], Topic: [{}]", message, appConfig.getUserTopic());
        log.info("----------------------------------");
        kafkaTemplate.send(appConfig.getUserTopic(), message);
    }
}
