package com.bikkadit.blogapp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.blogapp.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

	/**
	 * @author Shital
	 * @implNote This impl used for upload the image
	 */
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		log.info("Initiated request for upload the image");
		// File Name
		String name = file.getOriginalFilename();
		// abc.png

		// Random Name generate File
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		// FullPath
		String filePath = path + File.separator + fileName1;

		// Create Folder if Not Created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		log.info("Completed request for upload the image");
		// File Copy
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName1;

	}

	/**
	 * @implNote This impl is used to get resource
	 */
	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		log.info("Initiated request for serve file");
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		log.info("Completed request for serve file ");
		// DataBase Logic to return Inputstream
		return is;
	}

}
