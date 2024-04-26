package pro.linuxlab.reservation.superadmin.service;

public interface MongoFetch {
    String findAndDeleteInMongo(String key);
}
