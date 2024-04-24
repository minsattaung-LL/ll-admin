package pro.linuxlab.reservation.superadmin.business.site;

import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;

public interface ConsumerSiteBusiness {
    void createNewSite(MongoDto mongoDto);

    void updateSite(MongoDto mongoDto);

    void updateStatusOfSite(MongoDto mongoDto);
}
