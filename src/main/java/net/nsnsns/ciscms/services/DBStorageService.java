package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Document;
import net.nsnsns.ciscms.repos.DocumentRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DBStorageService implements StorageService {
    private final DocumentRepository documentRepository;

    DBStorageService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void store(MultipartFile file) {
        try {

            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            System.out.println("File content type:" + file.getContentType());
            document.setData(file.getInputStream().readAllBytes());
            documentRepository.save(document);
            System.out.println("Stored file in db");
        } catch (IOException e) {
            System.out.println("Could not store file");
        }

    }

    @Override
    public Resource loadAsResource(String filename) {
        Optional<Document> document = documentRepository.getByName(filename);
        if (document.isPresent()) {
            Document document1 = document.get();
            Resource byteResource = new ByteArrayResource(document1.getData());
            return new ByteArrayResource(document.get().getData());
        }

        return null;
    }
}
