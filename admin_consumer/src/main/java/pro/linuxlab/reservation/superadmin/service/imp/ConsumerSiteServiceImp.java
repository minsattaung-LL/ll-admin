package pro.linuxlab.reservation.superadmin.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.linuxlab.reservation.superadmin.entity.LLSiteConfig;
import pro.linuxlab.reservation.superadmin.repo.LLSiteConfigRepo;
import pro.linuxlab.reservation.superadmin.service.ConsumerSiteService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerSiteServiceImp implements ConsumerSiteService {
    final LLSiteConfigRepo llSiteConfigRepo;
    @Override
    public List<LLSiteConfig> getSiteList() {
        return llSiteConfigRepo.findAll();
    }

    @Override
    public void save(LLSiteConfig entity) {
        llSiteConfigRepo.save(entity);
    }
}
