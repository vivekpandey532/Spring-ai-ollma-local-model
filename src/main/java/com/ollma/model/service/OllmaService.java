package com.ollma.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllmaService {

    private final ChatClient chatClient;

    public OllmaService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
