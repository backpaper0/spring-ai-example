package com.example.chunking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLinesDocumentWriter implements DocumentWriter {

    private ObjectMapper objectMapper;
    private Path path;

    public JsonLinesDocumentWriter(ObjectMapper objectMapper, Path path) {
        this.objectMapper = objectMapper;
        this.path = path;
    }

    @Override
    public void accept(List<Document> documents) {
        try (var writer = Files.newBufferedWriter(path)) {
            for (var document : documents) {
                var line = objectMapper.writeValueAsString(document);
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
