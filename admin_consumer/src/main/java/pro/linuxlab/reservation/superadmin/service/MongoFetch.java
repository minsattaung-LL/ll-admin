package pro.linuxlab.reservation.superadmin.service;

public interface MongoFetch {
    Object findAndDeleteInMongo(String key);
}
