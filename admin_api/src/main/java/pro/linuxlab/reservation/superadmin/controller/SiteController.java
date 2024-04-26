package pro.linuxlab.reservation.superadmin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.linuxlab.reservation.superadmin.EnumPool;
import pro.linuxlab.reservation.superadmin.business.site.ISite;
import pro.linuxlab.reservation.superadmin.dto.site.SiteRequest;
import pro.linuxlab.reservation.superadmin.dto.site.SiteUpdateRequest;

@RestController
@CrossOrigin
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {
    final ISite iSite;
    @GetMapping("/map")
    public ResponseEntity<?> getSiteConfigList(){
        return ResponseEntity.ok(iSite.getSiteConfigList());
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSite(@RequestParam(required = false) String siteName,
                                     @RequestParam(required = false) String kcClientId,
                                     @RequestParam(required = false) String kcClientSecret,
                                     @RequestParam(required = false) String databaseUrl,
                                     @RequestParam(required = false) String databaseName,
                                     @RequestParam(required = false) String databaseUser,
                                     @RequestParam(required = false) String databasePassword,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) String siteUserStatus,
                                     @RequestParam(name = "offset", defaultValue = "0", required = false) int offset,
                                     @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                     @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) String sortBy,
                                     @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(iSite.getSite(siteName, kcClientId, kcClientSecret, databaseUrl, databaseName, databaseUser, databasePassword, description, siteUserStatus, offset, pageSize, sortBy, direction));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewSite(@Valid @RequestBody SiteRequest request) {
        return ResponseEntity.ok(iSite.createNewSite(request));
    }

    @PutMapping("/{siteId}/update")
    public ResponseEntity<?> updateSite(@PathVariable String siteId, @Valid @RequestBody SiteUpdateRequest request) {
        return ResponseEntity.ok(iSite.updateSite(siteId, request));
    }

    @PutMapping("/{siteId}/status")
    public ResponseEntity<?> changeSiteStatus (@PathVariable String siteId, @RequestParam EnumPool.SiteUserStatus siteUserStatus) {
        return ResponseEntity.ok(iSite.changeSiteStatus(siteId, siteUserStatus));
    }
}
