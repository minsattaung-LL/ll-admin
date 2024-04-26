//package pro.linuxlab.reservation.superadmin.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class KafkaTopicConfig {
//    @Bean
//    public NewTopic userTopic() {
//        return TopicBuilder.name("rms-admin-user-topic").partitions(3).build();
//    }
//    @Bean
//    public NewTopic siteTopic() {
//        return TopicBuilder.name("rms-admin-site-topic").partitions(3).build();
//    }
//}
