package com.example.web;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;

    @PostMapping("/load-docs")
    public void loadDocs() {
        vectorStore.add(List.of(new Document("harrison worked at kensho")));
    }

    @PostMapping("/rag")
    public Object rag(@RequestParam String question) {
        String answer = chatClient.prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(question).call().content();
        return answer;
    }
}
