package pro.linuxlab.reservation.superadmin.service;

public interface MongoService {
    void saveInMongo(String key, Object object);
}
