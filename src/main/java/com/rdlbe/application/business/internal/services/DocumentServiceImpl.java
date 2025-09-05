package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.DocumentDAO;
import com.rdlbe.application.business.internal.domains.Document;
import com.rdlbe.application.business.publishing.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
	private final DocumentDAO dao;

	public DocumentServiceImpl(DocumentDAO dao) {
		this.dao = dao;
	}
	@Override
	public List<Document> getDocsByUserId(Long userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public void saveDoc(Long userId, String doc) {
		dao.saveDoc(userId, doc);
	}

	@Override
	public boolean deleteDoc(Long id) {
		return dao.deleteDoc(id);
	}
}
