package pro.linuxlab.reservation.superadmin.service.imp;

import com.mongodb.client.model.IndexOptions;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.service.MongoService;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MongoServiceImp implements MongoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveInMongo(String key, Object object){
        Instant expirationTime = Instant.now().plus(Duration.ofHours(24));

        Document document = new Document();
        document.put("key", key);
        document.put("Object", object);

        document.put("expirationTime", expirationTime);

        mongoTemplate.insert(document, "rms-test");

        IndexOptions indexOptions = new IndexOptions().expireAfter(24L, TimeUnit.HOURS);

        mongoTemplate.getCollection("demo-test").createIndex(new Document("expirationTime", 1), indexOptions);
        log.info("Saved in mongo with 24 hours auto delete for key : [{}]", key);
    }
}
