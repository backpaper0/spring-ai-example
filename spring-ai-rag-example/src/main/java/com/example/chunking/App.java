package com.example.chunking;

import java.nio.file.Path;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        var sa = new SpringApplication(App.class);
        sa.setWebApplicationType(WebApplicationType.NONE);
        sa.run(args);
    }

    @Bean
    public ApplicationRunner readDocuments(ObjectMapper objectMapper) {
        return args -> {
            var input = "https://www.ipa.go.jp/security/vuln/websecurity/ug65p900000196e2-att/000017316.pdf";
            var output = Path.of("target", "documents.jsonl");

            var reader = new TikaDocumentReader(input);
            var transformer = new TokenTextSplitter();
            var writer = new JsonLinesDocumentWriter(objectMapper, output);

            writer.write(transformer.transform(reader.read()));
        };
    }
}
