package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.service.MongoFetch;

@Service
@Slf4j
public class MongoFetchImp implements MongoFetch {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Object findAndDeleteInMongo(String key) {
        Query query = new Query(Criteria.where("key").is(key));

        Document document = mongoTemplate.findOne(query, Document.class, "rms-test");
        Object object = null;
        if (document != null) {
            log.info("Found document: {}", document.toJson());
            object = document.get("Object");
            mongoTemplate.remove(query, "rms-test");
            log.info("Document with search '{}' deleted", key);
        } else {
            log.info("No document found with search '{}'", key);
        }
        return object;
    }
}
