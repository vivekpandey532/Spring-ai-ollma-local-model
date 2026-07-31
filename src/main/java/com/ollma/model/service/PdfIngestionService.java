package com.ollma.model.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfIngestionService {

    private final VectorStore vectorStore;

    private final TokenTextSplitter tokenTextSplitter;

    public PdfIngestionService(VectorStore vectorStore, TokenTextSplitter tokenTextSplitter) {
        this.vectorStore = vectorStore;
        this.tokenTextSplitter = tokenTextSplitter;
    }

    public void ingest(Path pdfPath) throws IOException {

        try (PDDocument pdf = Loader.loadPDF(pdfPath.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            List<Document> allChunks = new ArrayList<>();

            for (int page = 1; page < pdf.getNumberOfPages(); page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                String pageText = stripper.getText(pdf);

                if (pageText == null || pageText.isBlank()) {
                    continue;
                }
                Document pageDocument = new Document(pageText, Map.of("Source", pdfPath.getFileName(),
                "page", page));
                List<Document> chunks = tokenTextSplitter.apply(List.of(pageDocument));
                allChunks.addAll(chunks);
                //chunks.forEach(System.out::println);
            }
            vectorStore.write(allChunks);
        }
    }

    public String search(String question) {

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(5)
                        .build());

        if (documents.isEmpty()) {
            throw new RuntimeException("No relevant information found.");
        }
        return documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));
    }

}
