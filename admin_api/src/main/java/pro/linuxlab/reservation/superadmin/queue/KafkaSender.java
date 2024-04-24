package pro.linuxlab.reservation.superadmin.queue;

public interface KafkaSender {
    void sendSiteMessage(String message);

    void sendUserMessage(String message);
}
