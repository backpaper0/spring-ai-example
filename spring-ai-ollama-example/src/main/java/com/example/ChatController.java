package com.example;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ChatController {

    @Autowired
    private ChatClient chatClient;

    @PostMapping("/chat")
    public String chat(@RequestParam String query) {
        return chatClient
                .prompt(query)
                .call()
                .content();
    }
}
