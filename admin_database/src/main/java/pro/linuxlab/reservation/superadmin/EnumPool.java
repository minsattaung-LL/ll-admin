package pro.linuxlab.reservation.superadmin;

public class EnumPool {
    public enum Gender {
        Male, Female
    }
    public enum SiteUserStatus {
        Active, Block;
    }
    public enum PartnerStatus {
        Pending, Approved, Declined
    }
    public enum InquiryStatus {
        Pending, Done
    }
    public enum UserRole {
        Admin, Staff
    }
    public enum EntityConfig {
        CUSTOMER, CUSTOMER_INQUIRY, PARTNER_WITH_US, SITE_CONFIG, SITE_STAFF, USER
    }
    public enum MongoDataConfigFunc {
        CREATE, UPDATE, STATUS
    }
    public enum KeyPrefix {
        SITE("site"), USER("user");
        public String prefix;

        KeyPrefix(String prefix) {
            this.prefix = prefix;
        }
        public static String getPrefix(KeyPrefix keyPrefix) {
            return keyPrefix.prefix;
        }
    }
}
