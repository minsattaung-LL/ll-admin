package pro.linuxlab.reservation.superadmin.queue;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pro.linuxlab.reservation.superadmin.business.site.ConsumerSiteBusiness;
import pro.linuxlab.reservation.superadmin.business.user.ConsumerUserBusiness;
import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;
import pro.linuxlab.reservation.superadmin.service.MongoFetch;
import pro.linuxlab.reservation.superadmin.util.Util;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReceiveMessageImp implements ReceiveMessage{
    final ConsumerSiteBusiness consumerSiteBusiness;
    final ConsumerUserBusiness consumerUserBusiness;
    final MongoFetch mongoFetch;
    final Util util;

    @Override
    @KafkaListener(
            topics  = "${spring.kafka.template.site.topic}",
            groupId = "${spring.kafka.consumer.site.group-id}"
    )
    public void siteListener(ConsumerRecord<String, String> consumerRecord) {
        long start = System.currentTimeMillis();
        try {
            if (consumerRecord.value() == null) {
                log.error("Message null --> return");
                return;
            }
            log.info("Received Site Topic message [{}], offset [{}]", consumerRecord.value(), consumerRecord.offset());
            String returnString = mongoFetch.findAndDeleteInMongo(consumerRecord.value());
            if (StringUtils.isBlank(returnString)) {
                log.error("[{}] is not found in MongoDB", consumerRecord.value());
                return;
            }
            MongoDto mongoDto = util.toObject(returnString, MongoDto.class);
            switch (mongoDto.getMongoDataConfigFunc()) {
                case CREATE -> {
                    consumerSiteBusiness.createNewSite(mongoDto);
                }
                case UPDATE -> {
                    consumerSiteBusiness.updateSite(mongoDto);
                }
                case STATUS -> {
                    consumerSiteBusiness.updateStatusOfSite(mongoDto);
                }
                default -> {
                    log.error("No Mongo data Config Function found");
                }
            }
        } catch(Exception e) {
            log.error("Consumer Exception: due to ", e);
        } finally {
            long end = System.currentTimeMillis();
            log.info("Site Process in [{}] ms",(end - start));
        }
    }

    @Override
    @KafkaListener(
            topics  = "${spring.kafka.template.user.topic}",
            groupId = "${spring.kafka.consumer.user.group-id}"
    )
    public void userListener(ConsumerRecord<String, String> consumerRecord) {
        long start = System.currentTimeMillis();
        try {
            if (consumerRecord.value() == null) {
                log.error("Message null --> return");
                return;
            }
            log.info("Received User Topic message [{}], offset [{}]", consumerRecord.value(), consumerRecord.offset());
            String returnString = mongoFetch.findAndDeleteInMongo(consumerRecord.value());
            if (StringUtils.isBlank(returnString)) {
                log.error("[{}] is not found in MongoDB", consumerRecord.value());
                return;
            }
            MongoDto mongoDto = util.toObject(returnString, MongoDto.class);
            log.info("Retrieved data from mongo : {}", util.toJson(mongoDto));
            switch (mongoDto.getMongoDataConfigFunc()) {
                case CREATE -> {
                    consumerUserBusiness.createNewUser(mongoDto);
                }
                case UPDATE -> {
                    consumerUserBusiness.updateUser(mongoDto);
                }
                case STATUS -> {
                    consumerUserBusiness.updateStatusOfUser(mongoDto);
                }
                default -> {
                    log.error("No Mongo data Config Function found in : [{}]", util.toJson(mongoDto));
                }
            }
        } catch(Exception e) {
            log.error("Consumer Exception: due to ", e);
        } finally {
            long end = System.currentTimeMillis();
            log.info("User Process in [{}] ms",(end - start));
        }
    }
}
