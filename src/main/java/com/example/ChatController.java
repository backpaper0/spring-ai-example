package com.example;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private StreamingChatClient streamingChatClient;

    @PostMapping
    public Object post(@RequestParam String text) {
        Prompt prompt = new Prompt(text);
        ChatResponse resp = chatClient.call(prompt);
        return resp.getResult();
    }

    @PostMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String text) {
        Prompt prompt = new Prompt(text);
        return streamingChatClient.stream(prompt)
                .map(resp -> resp.getResult().getOutput().getContent());
    }
}
