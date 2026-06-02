package com.ollma.model.controller;

import com.ollma.model.service.OllmaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllmaController {

    private final OllmaService ollmaService;

    public OllmaController(OllmaService ollmaService) {
        this.ollmaService = ollmaService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return ollmaService.ask(question);
    }
}
