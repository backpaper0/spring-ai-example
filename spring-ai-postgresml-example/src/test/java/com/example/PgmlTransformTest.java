package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

/**
 * PostgresMLのpgml.transform関数を試すだけのテストクラスです。
 * 
 */
@SpringBootTest
public class PgmlTransformTest {

    @Autowired
    JdbcClient jdbc;

    @Test
    void testTransform() throws Exception {
        var query = """
                {
                    "question": "Where and when was Einstein born?",
                    "context": "Albert Einstein (born 14 March 1879) was a German-born theoretical physicist, widely held to be one of the greatest and most influential scientists of all time"
                }
                """
                .stripIndent();

        String transform = jdbc.sql("select pgml.transform('question-answering', inputs => ARRAY[ :query ])")
                .param("query", query)
                .query(SingleColumnRowMapper.newInstance(String.class))
                .single();

        // 以下のようなJSONが返される
        // {"end": 35, "score": 0.947870910167694, "start": 22, "answer": "14 March 1879"}，
        System.out.println(transform);
    }
}
