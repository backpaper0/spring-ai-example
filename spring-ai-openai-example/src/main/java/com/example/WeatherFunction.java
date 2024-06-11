package com.example;

import java.util.function.Function;

public class WeatherFunction implements Function<WeatherFunction.Request, WeatherFunction.Response> {

    public record Request(String location) {
    }

    public record Response(double temperature) {
    }

    @Override
    public Response apply(Request request) {
        // 以前(GPT-3.5)は「大阪の気温は？」と聞いても"osaka"とローマ字表現にして関数呼び出しを行なっていたが、
        // GPT-4oは"大阪"とそのまま渡してくるようになったっぽい。
        return new Response(switch (request.location().toLowerCase()) {
            case "osaka" -> 8.0;
            case "大阪" -> 8.0;
            case "tokyo" -> 10.0;
            case "東京" -> 10.0;
            case "hokkaido" -> 1.0;
            case "北海道" -> 1.0;
            default -> 0.0;
        });
    }
}