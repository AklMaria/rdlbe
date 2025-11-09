package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.DocumentService;
import com.rdlbe.application.views.DocumentItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@RestController
@RequestMapping("/docs")
@Slf4j
public class DocumentRestController {

    private final DocumentService documentService;

    public DocumentRestController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{userId}")
    public List<DocumentItem> getDocsByUserId(@PathVariable("userId") Long userId) {
        return documentService.getDocsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDocById(@PathVariable("id") Long id) {
        return documentService.deleteDoc(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadDoc(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        log.info("Ricevuto upload '{}' per user {}", file.getOriginalFilename(), userId);
        documentService.saveDoc(userId, file);
    }
    // ✅ NUOVO ENDPOINT DI DOWNLOAD documento
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        DocumentItem document = documentService.findById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        Resource fileResource = documentService.getDocumentAsResource(document);
        if (fileResource == null || !fileResource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .body(fileResource);
    }
}
