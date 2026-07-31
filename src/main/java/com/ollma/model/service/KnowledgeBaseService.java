package com.ollma.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class KnowledgeBaseService {

    @Value("classpath:prompts/java-assitant-system-prompt.st")
    private Resource javaPrompt;

    private final ChatClient chatClient;
    private final PdfIngestionService ingestionService;

    public KnowledgeBaseService(ChatClient chatClient, PdfIngestionService pdfIngestionService) {
        this.chatClient = chatClient;
        this.ingestionService = pdfIngestionService;
    }

    public String ask(String question) {
        String questionSimilarSearchResult = ingestionService.search(question);
         String response = chatClient.prompt()
                .system(spec -> spec.text(javaPrompt))
                .user(questionSimilarSearchResult)
                .call()
                .content();
        System.out.println("Response from model: " + response);
        return response;
    }

    public Flux<String> askStream(String question) {
        Flux<String> response = chatClient.prompt()
                .system(spec -> spec.text(javaPrompt))
                .user(question)
                .stream()
                .content()
                .bufferTimeout(20, Duration.ofMillis(200))
                .map(parts -> String.join("", parts));

        System.out.println("Response from model: " + response);

        return response;

    }
}
