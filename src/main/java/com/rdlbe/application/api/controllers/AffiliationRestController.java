package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.AffiliationService;
import com.rdlbe.application.views.AffiliationItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/affiliations")
@Slf4j
public class AffiliationRestController {

    private final AffiliationService affiliationService;

    public AffiliationRestController(AffiliationService affiliationService) {
        this.affiliationService = affiliationService;
    }

    // --- GET: tutte le affiliations ---
    @GetMapping
    public List<AffiliationItem> getAffiliations() {
        log.debug("Getting all affiliations");
        return affiliationService.getAffiliations();
    }

    // --- POST: crea nuova affiliation ---
    @PostMapping
    public AffiliationItem createAffiliation(@RequestBody AffiliationItem affiliation) {
        log.debug("Creating new affiliation: {}", affiliation);
        return affiliationService.createAffiliation(affiliation);
    }

    // --- DELETE: elimina affiliation ---
    @DeleteMapping("/{id}")
    public void deleteAffiliation(@PathVariable("id") Long id) {
        affiliationService.deleteAffiliation(id);
    }
}
