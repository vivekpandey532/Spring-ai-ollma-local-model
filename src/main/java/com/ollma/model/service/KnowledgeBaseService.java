package com.ollma.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeBaseService {

    @Value("classpath:prompts/java-assitant-system-prompt.st")
    private Resource javaPrompt;

    private final ChatClient chatClient;

    public KnowledgeBaseService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String question) {
        return chatClient.prompt()
                .system(spec -> spec.text(javaPrompt))
                .user(question)
                .call()
                .content();
    }
}
