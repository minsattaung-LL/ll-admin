package pro.linuxlab.reservation.superadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.linuxlab.reservation.superadmin.business.dashboard.IDashboard;

@RestController
@CrossOrigin
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    final IDashboard iDashboard;
}
