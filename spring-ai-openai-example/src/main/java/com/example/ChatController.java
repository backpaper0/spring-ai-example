package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * Chat Completionsを試す。
 * 
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatClient chatClient;

    @PostMapping
    public Object post(@RequestParam String text) {
        ChatResponse resp = chatClient.prompt(text).call().chatResponse();
        return resp.getResult();
    }

    @PostMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String text) {
        return chatClient.prompt(text).stream().chatResponse()
                .map(resp -> resp.getResult().getOutput().getText());
    }

    @PostMapping("/fn")
    public Object postWithFunctionCalling(@RequestParam String text) {
        ChatResponse resp = chatClient.prompt(text)
                .tools("weatherFunction")
                .call().chatResponse();
        return resp.getResult().getOutput();
    }
}
