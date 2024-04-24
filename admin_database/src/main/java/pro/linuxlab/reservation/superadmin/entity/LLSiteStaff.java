package pro.linuxlab.reservation.superadmin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ll_site_staff", schema = "system_config_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LLSiteStaff {
    @Id
    @Column(name = "site_staff_id")
    String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    LLUser llUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id",nullable = false)
    LLSiteConfig llSiteConfig;
}
