package com.esprit.gestionAuth.services.Interface;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
	public boolean addFile(MultipartFile file);
}
