package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.DocumentDAO;
import com.rdlbe.application.business.internal.domains.Document;
import com.rdlbe.application.business.publishing.DocumentService;
import com.rdlbe.application.views.DocumentItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDAO dao;
    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

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

    // ✅ NUOVI METODI PER IL DOWNLOAD

    @Override
    public DocumentItem findById(Long id) {
        Document doc = dao.findById(id);
        if (doc == null) {
            return null;
        }

        DocumentItem item = new DocumentItem();
        item.setId(doc.getId());
        item.setFileName(doc.getFileName());
        item.setContentType(doc.getContentType());
        return item;
    }

    @Override
    public Resource getDocumentAsResource(DocumentItem document) {
        try {
            // Caso 1️⃣: se i file vengono salvati fisicamente
            Path filePath = Paths.get(uploadDir).resolve(document.getFileName()).normalize();
            if (Files.exists(filePath)) {
                return new FileSystemResource(filePath);
            }

            // Caso 2️⃣: se i file vengono salvati in DB come byte[]
            byte[] fileBytes = dao.getFileBytesById(document.getId());
            if (fileBytes != null) {
                return new ByteArrayResource(fileBytes);
            }

            log.warn("⚠️ Documento non trovato: {}", document.getFileName());
            return null;

        } catch (Exception e) {
            log.error("Errore durante la lettura del documento", e);
            return null;
        }
    }





}
