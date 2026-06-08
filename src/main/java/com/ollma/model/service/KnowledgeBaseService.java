package com.ollma.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

@Service
public class KnowledgeBaseService {

    @Value("classpath:prompts/java-assitant-system-prompt.st")
    private Resource javaPrompt;

    private final ChatClient chatClient;

    public KnowledgeBaseService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String question) {
        ChatResponse chatResponse = chatClient.prompt()
                .system(spec -> spec.text(javaPrompt))
                .user(question)
                .call()
                .chatResponse();
        System.out.println("Response from model: " + chatResponse);
        return Objects.requireNonNull(Objects.requireNonNull(chatResponse).getResult()).getOutput().getText();
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
