# Spring AI example

OpenAIを使ってSpring AIを試します。

## 準備

`spring.ai.openai.api-key`プロパティへOpenAIのAPIキーを設定してください。

## 起動

```sh
./mvnw spring-boot:run
```

## 実行

### Chat Completions API

[Chat Completions API](https://platform.openai.com/docs/guides/text-generation/chat-completions-api)によるテキスト生成を試します。

次のコマンドを実行してください。

```sh
curl localhost:8080/chat -s -d text="Spring Bootについて100文字程度で説明してください 。" | jq
```

次のような結果が得られます。

```
{
  "metadata": {
    "contentFilterMetadata": null,
    "finishReason": "STOP"
  },
  "output": {
    "content": "Spring Bootは、Javaプラットフォーム用のオープンソースのマイクロサービスフレームワークであり、簡単にスタンドアロンのSpringアプリケーションを作成できるように設計されています。Spring Bootは、自動構成や自動設定などの機能を提供し、開発者がアプリケーションのビジネスロジックに焦点を当てることができるようにサポートします。",
    "properties": {
      "role": "ASSISTANT"
    },
    "messageType": "ASSISTANT"
  }
}
```

ストリーミングも行えます。

```sh
curl localhost:8080/chat/stream -s -N -H "Accept: text/event-stream" -d text="こんにちは"
```

結果は次の通りです。

```
data:

data:こんにちは

data:！

data:お

data:困

data:り

data:ご

data:と

data:が

data:あ

data:れ

data:ば

data:お

data:手

data:伝

data:い

data:します

data:の

data:で

data:、

data:ど

data:う

data:ぞ

data:お

data:気

data:軽

data:に

data:お

data:知

data:ら

data:せ

data:ください

data:。
```
