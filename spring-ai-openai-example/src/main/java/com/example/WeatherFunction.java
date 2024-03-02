package com.example;

import java.util.function.Function;

public class WeatherFunction implements Function<WeatherFunction.Request, WeatherFunction.Response> {

    public record Request(String location) {
    }

    public record Response(double temperature) {
    }

    @Override
    public Response apply(Request request) {
        return new Response(switch (request.location().toLowerCase()) {
            case "osaka" -> 8.0;
            case "tokyo" -> 10.0;
            case "hokkaido" -> 1.0;
            default -> 0.0;
        });
    }
}