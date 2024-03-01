# Spring AI OpenAI example

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
curl localhost:8080/chat/stream -s -N -d text="こんにちは"
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

### ベクトル検索

OpenAIの[Embeddings](https://platform.openai.com/docs/guides/embeddings)でベクトル化し、[pgvector](https://github.com/pgvector/pgvector)へ格納します。

まずはドキュメントのサンプルを追加します。

```sh
curl localhost:8080/search -XPOST
```

検索します。

```sh
curl localhost:8080/search -s -G -d q=Spring | jq
```

結果は次の通りです。

```
[
  "Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!"
]
```

## その他の情報

- `org.springframework.ai.autoconfigure.openai.OpenAiChatProperties`を見たところ、デフォルトの言語モデルは`gpt-3.5-turbo`みたい
- `spring-boot-docker-compose`を入れたのに起動時に`spring.datasource.url`が無いと言われた。動的に設定されるんじゃなかったっけ？
