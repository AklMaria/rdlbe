package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.internal.domains.Document;
import com.rdlbe.application.business.publishing.DocumentService;
import com.rdlbe.application.views.DocumentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

	@DeleteMapping("/{userId}")
	public boolean deleteDocByUserId(@PathVariable("userId") Long userId) {
		return documentService.deleteDoc(userId);
	}

	@PostMapping
	public void createDoc(@RequestBody DocumentRequest documentRequest) {
		documentService.saveDoc(documentRequest.getUserId(), documentRequest.getContent());
	}
}
