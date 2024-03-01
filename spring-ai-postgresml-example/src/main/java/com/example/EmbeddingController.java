package com.example;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * PostgresMLの埋め込みを試す。
 * 
 */
@RestController
@RequestMapping("/embedding")
public class EmbeddingController {

    @Autowired
    private EmbeddingClient embeddingClient;

    @PostMapping
    public Object post(@RequestParam String text) {
        return embeddingClient.embed(text);
    }
}
