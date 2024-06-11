package com.example;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.evaluation.BasicEvaluationTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EvaluationTest extends BasicEvaluationTest {

    @Test
    void testEvaluate() {
        String question = "Where is France and what is it’s capital?";
        ChatResponse response = new ChatResponse(
                List.of(new Generation("France is in western Europe and Paris is its capital.")));
        boolean factBased = false;
        evaluateQuestionAndAnswer(question, response, factBased);
    }
}
