package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ベクトル検索を試す。
 * 
 */
@RestController
@RequestMapping("/search")
public class VectorSearchController {

    @Autowired
    private VectorStore vectorStore;

    @GetMapping
    public Object search(@RequestParam String q) {
        SearchRequest request = SearchRequest.query(q).withTopK(1);
        // 内部では検索クエリーをOpenAIのEmbeddingでベクトル化し、検索を行っている
        List<Document> documents = vectorStore.similaritySearch(request);
        return documents.stream().map(doc -> doc.getContent()).toList();
    }

    /**
     * データの準備。
     * 
     */
    @PostMapping
    public void addDocuments() {
        List<Document> documents = List.of(
                new Document(
                        "Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!",
                        Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.",
                        Map.of("meta2", "meta2")));
        vectorStore.add(documents);
    }
}
