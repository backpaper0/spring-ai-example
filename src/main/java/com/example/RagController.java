package com.example;

import java.util.Iterator;
import java.util.List;

import org.springframework.ai.chat.ChatClient;
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

    @PostMapping("/rag")
    public Object rag(@RequestParam String question) {

        // 検索
        List<Document> docs = vectorStore.similaritySearch(question);
        Iterator<Document> iter = docs.iterator();
        if (!iter.hasNext()) {
            // 検索に何もヒットしなかった
            return "I do not know.";
        }

        // 生成
        String context = iter.next().getContent();
        String prompt = """
                Answer the question based only on the following context:
                %2$s

                Question: %1$s
                """.formatted(question, context);
        String answer = chatClient.call(prompt);

        return answer;
    }
}
