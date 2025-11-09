package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.DocumentItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.util.List;

public interface DocumentService {
    List<DocumentItem> getDocsByUserId(Long userId);
    void saveDoc(Long userId, MultipartFile file);
    boolean deleteDoc(Long id);
    // ✅ Nuovi metodi per il download
    DocumentItem findById(Long id);
    Resource getDocumentAsResource(DocumentItem document);
}
