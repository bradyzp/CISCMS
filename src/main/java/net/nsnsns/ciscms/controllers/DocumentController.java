package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.services.DBStorageService;
import net.nsnsns.ciscms.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/course/documents")
public class DocumentController {
    private final StorageService storageService;

    DocumentController(DBStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String documentUploader() {
        return "document_uploader";
    }

    @GetMapping("/view/{name}")
    public void viewDocument(@PathVariable(name = "name") String name, HttpServletResponse response) {
        Resource resource = storageService.loadAsResource(name);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + name);
        try {
            resource.getInputStream().transferTo(response.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to return document");
        }


    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file) {
        System.out.println("Uploading your file");
        storageService.store(file);

        return "redirect:/course";
    }
}
