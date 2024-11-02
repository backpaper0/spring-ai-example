package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.ai.evaluation.BasicEvaluationTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EvaluationTest extends BasicEvaluationTest {

    @Test
    void testEvaluate() {
        String question = "Where is France and what is it’s capital?";
        String answer = "France is in western Europe and Paris is its capital.";
        boolean factBased = false;
        evaluateQuestionAndAnswer(question, answer, factBased);
    }
}
