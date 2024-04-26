package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.service.MongoFetch;
import pro.linuxlab.reservation.superadmin.util.Util;

@Service
@Slf4j
public class MongoFetchImp implements MongoFetch {

    @Autowired
    private MongoTemplate mongoTemplate;
    final Util util;

    public MongoFetchImp(Util util) {
        this.util = util;
    }

    @Override
    public String findAndDeleteInMongo(String key) {
        Query query = new Query(Criteria.where("key").is(key));

        Document document = mongoTemplate.findOne(query, Document.class, "rms-test");
        String returnObject = null;
        if (document != null) {
            log.info("Found document [{}]", document.get("_id"));
            returnObject = util.toJson(document.get("Object"));
            log.info("Found Json : [{}]", returnObject);
//            mongoTemplate.remove(query, "rms-test");
//            log.info("Document with search '{}' deleted", key);
        } else {
            log.info("No document found with search '{}'", key);
        }
        return returnObject;
    }
}
