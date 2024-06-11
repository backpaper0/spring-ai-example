package com.example;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
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

    @PostMapping("/rag1")
    public Object rag1(@RequestParam String question) {

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
        String answer = chatClient.prompt().user(prompt).call().content();

        return answer;
    }

    @PostMapping("/rag2")
    public Object rag2(@RequestParam String question, @RequestParam(defaultValue = "") List<String> chatHistory) {
        // https://python.langchain.com/docs/expression_language/cookbook/retrieval#conversational-retrieval-chain

        String condenseQuestionPromt = """
                Given the following conversation and a follow up question, rephrase the follow up question to be a standalone question, in its original language.

                Chat History:
                %2$s
                Follow Up Input: %1$s
                Standalone question:
                """
                .formatted(
                        question,
                        chatHistory.stream().collect(Collectors.joining("\n")))
                .strip();

        String input = chatClient.prompt().user(condenseQuestionPromt).call().content();

        // 検索
        List<Document> docs = vectorStore.similaritySearch(input);
        String context = docs.stream().map(doc -> doc.getContent()).collect(Collectors.joining("\n\n"));

        // 生成
        String answerPrompt = """
                Answer the question based only on the following context:
                %2$s

                Question: %1$s
                """.formatted(input, context);
        String answer = chatClient.prompt().user(answerPrompt).call().content();

        return answer;
    }
}
