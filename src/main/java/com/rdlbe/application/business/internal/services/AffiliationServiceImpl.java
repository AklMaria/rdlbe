package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.AffiliationDAO;
import com.rdlbe.application.business.internal.domains.Affiliation;
import com.rdlbe.application.business.publishing.AffiliationService;
import com.rdlbe.application.views.AffiliationItem;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AffiliationServiceImpl implements AffiliationService {

    private final AffiliationDAO affiliationDAO;
    private final ModelMapper modelMapper;

    public AffiliationServiceImpl(AffiliationDAO affiliationDAO, ModelMapper modelMapper) {
        this.affiliationDAO = affiliationDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AffiliationItem> getAffiliations() {
        List<Affiliation> affiliations = affiliationDAO.find(null);
        return affiliations.stream()
                .map(affiliation -> {
                    AffiliationItem item = new AffiliationItem();
                    item.setId(affiliation.getId());
                    item.setName(affiliation.getName());
                    item.setLink(affiliation.getLink());
                    return item;
                })
                .toList();
    }

    @Override
    public AffiliationItem createAffiliation(AffiliationItem affiliationDto) {
        // Mappa il DTO in entità
        Affiliation classroom = modelMapper.map(affiliationDto, Affiliation.class);

        Long id = affiliationDAO.create(classroom); // Il DAO deve restituire l'ID creato
        classroom.setId(id);
        return modelMapper.map(classroom, AffiliationItem.class);
    }

    @Override
    public void deleteAffiliation(Long id) {
        affiliationDAO.delete(id, null); // se non serve idUtenteAggiornamento, passiamo null
    }

}
