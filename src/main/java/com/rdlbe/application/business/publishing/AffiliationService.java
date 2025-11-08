package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.AffiliationItem;

import java.util.List;

public interface AffiliationService {

    List<AffiliationItem> getAffiliations();
    AffiliationItem createAffiliation(AffiliationItem affiliation);
    void deleteAffiliation(Long id);
}
