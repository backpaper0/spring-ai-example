package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
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
        Prompt prompt = new Prompt(text);
        ChatResponse resp = chatClient.prompt(prompt).call().chatResponse();
        return resp.getResult();
    }

    @PostMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String text) {
        Prompt prompt = new Prompt(text);
        return chatClient.prompt(prompt).stream().chatResponse()
                .map(resp -> resp.getResult().getOutput().getContent());
    }

    @PostMapping("/fn")
    public Object postWithFunctionCalling(@RequestParam String text) {
        ChatOptions chatOptions = OpenAiChatOptions.builder()
                .withFunction("weatherFunction")
                .build();
        Prompt prompt = new Prompt(text, chatOptions);
        ChatResponse resp = chatClient.prompt(prompt).call().chatResponse();
        return resp.getResult().getOutput();
    }
}
