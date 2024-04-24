package pro.linuxlab.reservation.superadmin.business.user;

import pro.linuxlab.reservation.superadmin.dto.mongo.MongoDto;

public interface ConsumerUserBusiness {
    void createNewUser(MongoDto mongoDto);

    void updateUser(MongoDto mongoDto);

    void updateStatusOfUser(MongoDto mongoDto);
}
