package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatClient chatClient;

    @PostMapping
    public Object post(@RequestParam String query) {
        ChatResponse resp = chatClient.prompt(query).call().chatResponse();
        return resp.getResult();
    }
}
