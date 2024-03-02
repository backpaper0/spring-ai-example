package com.example;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    @Description("指定された場所の気温を取得する。")
    Function<WeatherFunction.Request, WeatherFunction.Response> weatherFunction() {
        return new WeatherFunction();
    }
}
