package com.ollma.model.controller;

import com.ollma.model.service.KnowledgeBaseService;
import com.ollma.model.service.OllmaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OllmaController {

    private final OllmaService ollmaService;
    private final KnowledgeBaseService knowledgeBaseService;

    public OllmaController(OllmaService ollmaService, KnowledgeBaseService knowledgeBaseService) {
        this.ollmaService = ollmaService;
        this.knowledgeBaseService = knowledgeBaseService;
    }

    /***
     * API used to ask normally intract with Model.
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

    /***
     * API used with prompt template to interact with model
     * @param question Question asked with model
     * @return Answer to the user
     */
//    @GetMapping(
//            value = "/java-assistant",
//            produces = MediaType.TEXT_PLAIN_VALUE)
//    public Flux<String> stream(
//            @RequestParam String question) {
//        return knowledgeBaseService.askStream(question);
//
//
//    }
}
