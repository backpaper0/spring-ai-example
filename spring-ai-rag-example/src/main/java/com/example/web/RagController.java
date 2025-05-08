package com.example.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.QueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
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
    @Autowired
    private ChatMemory chatMemory;

    @PostMapping("/rag1")
    public Object rag1(@RequestParam String question) {

        String answer = chatClient.prompt()
                .advisors(
                        new QuestionAnswerAdvisor(vectorStore),
                        new SimpleLoggerAdvisor())
                .user(question).call().content();

        return answer;
    }

    @PostMapping("/rag2")
    public Object rag2(@RequestParam String question, @RequestParam(defaultValue = "") List<String> chatHistory) {
        // https://python.langchain.com/docs/expression_language/cookbook/retrieval#conversational-retrieval-chain

        QueryTransformer queryTransformer = query -> {

            String condenseQuestionPromt = """
                    Given the following conversation and a follow up question, rephrase the follow up question to be a standalone question, in its original language.

                    Chat History:
                    {chat_history}
                    Follow Up Input: {question}
                    Standalone question:
                    """;

            String input = chatClient.prompt().user(user -> user
                    .text(condenseQuestionPromt)
                    .param("question", query.text())
                    .param("chat_history", chatHistory.stream().collect(Collectors.joining("\n")))).call().content();

            return new Query(input);
        };

        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .build();

        QueryAugmenter queryAugmenter = (query, documents) -> {

            String answerPrompt = """
                    Answer the question based only on the following context:
                    {context}

                    Question: {question}
                    """;

            PromptTemplate template = new PromptTemplate(answerPrompt);
            template.add("question", query.text());
            template.add("context",
                    documents.stream().map(doc -> doc.getText()).collect(Collectors.joining("\n\n")));

            return new Query(template.render());
        };

        RetrievalAugmentationAdvisor rag = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(queryTransformer)
                .documentRetriever(documentRetriever)
                .queryAugmenter(queryAugmenter)
                .build();

        String answer = chatClient.prompt().user(question).advisors(rag).call().content();

        return answer;
    }

    @PostMapping("/chat")
    public Object chat(@RequestParam String query, @RequestParam String conversationId) {
        String answer = chatClient.prompt()
                .advisors(new MessageChatMemoryAdvisor(chatMemory))
                .advisors(advisor -> advisor
                        .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
                .user(query)
                .call().content();
        return answer;
    }
}
