package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.DocumentDAO;
import com.rdlbe.application.business.internal.domains.Document;
import com.rdlbe.application.business.publishing.DocumentService;
import com.rdlbe.application.views.DocumentItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDAO dao;

    public DocumentServiceImpl(DocumentDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<DocumentItem> getDocsByUserId(Long userId) {
        List<Document> documents = dao.findByUserId(userId);
        return documents.stream()
                .map(doc -> {
                    DocumentItem item = new DocumentItem();
                    item.setFileName(doc.getFileName());
                    item.setContentType(doc.getContentType());
                    item.setId(doc.getId());
                    return item;
                })
                .toList();
    }

    @Override
    public void saveDoc(Long userId, MultipartFile file) {
        try {
            dao.saveDoc(userId, file.getBytes(), file.getOriginalFilename(), file.getContentType());
            log.info("File '{}' caricato con successo per l’utente {}", file.getOriginalFilename(), userId);
        } catch (IOException e) {
            log.error("Errore durante il salvataggio del file", e);
            throw new RuntimeException("Errore durante il salvataggio del documento", e);
        }
    }

    @Override
    public boolean deleteDoc(Long id) {
        return dao.deleteDoc(id);
    }
}
