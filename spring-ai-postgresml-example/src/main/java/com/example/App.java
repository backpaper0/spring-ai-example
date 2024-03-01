package com.example;

import org.springframework.ai.autoconfigure.postgresml.PostgresMlAutoConfiguration;
import org.springframework.ai.postgresml.PostgresMlEmbeddingClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * なぜか{@link PostgresMlAutoConfiguration}が動いてくれない、、、
     * 
     * @param jdbcTemplate
     * @return
     */
    @Bean
    PostgresMlEmbeddingClient postgresMlEmbeddingClient(JdbcTemplate jdbcTemplate) {
        return new PostgresMlEmbeddingClient(jdbcTemplate);
    }
}
