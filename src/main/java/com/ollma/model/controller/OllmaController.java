package com.ollma.model.controller;

import com.ollma.model.DTOs.ModelAnswer;
import com.ollma.model.service.KnowledgeBaseService;
import com.ollma.model.service.OllmaService;
import com.ollma.model.service.PdfIngestionService;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.document.Document;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class OllmaController {

    private final OllmaService ollmaService;
    private final KnowledgeBaseService knowledgeBaseService;
    private final PdfIngestionService ingestionService;

    public OllmaController(OllmaService ollmaService, KnowledgeBaseService knowledgeBaseService, PdfIngestionService ingestionService) {
        this.ollmaService = ollmaService;
        this.knowledgeBaseService = knowledgeBaseService;
        this.ingestionService = ingestionService;
    }

    /***
     * API used to ask normally interact with Model.
     * @param question Question asked with model
     * @return Answer to user
     */
    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return ollmaService.ask(question);
    }

    /***
     * API used with prompt template to interact with model
     * @param question Question asked with model
     * @return Answer to the user
     */
    @GetMapping("/java-assistant")
    public String askJavaAssistant(@RequestParam String question) {
        return knowledgeBaseService.ask(question);
    }

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {

        Path temp = Files.createTempFile("pdf", ".pdf");
        file.transferTo(temp);
        ingestionService.ingest(temp);
        return "Uploaded";
    }

    @GetMapping("/search")
    public String searchVector(@RequestParam String question) {
        return ingestionService.search(question);
    }
}
