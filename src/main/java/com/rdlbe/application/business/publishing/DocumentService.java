package com.rdlbe.application.business.publishing;

import com.rdlbe.application.business.internal.domains.Document;

import java.util.List;

public interface DocumentService {
	List<DocumentItem> getDocsByUserId(Long userId);
	void saveDoc(Long userId, String doc);
	boolean deleteDoc(Long id);
}
