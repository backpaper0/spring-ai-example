# Spring AI OpenAI example

## 準備

`spring.ai.openai.api-key`プロパティへOpenAIのAPIキーを設定してください。

## 起動

```sh
mvn spring-boot:run
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

```json
[
  "Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!"
]
```

### Function calling

[Function calling](https://platform.openai.com/docs/guides/function-calling)を試します。

```sh
curl localhost:8080/chat/fn -s -d text=大阪の気温を教えてください。 | jq
```

```json
{
  "content": "大阪の気温は現在8.0度です。",
  "properties": {
    "role": "ASSISTANT"
  },
  "messageType": "ASSISTANT"
}
```

```sh
curl localhost:8080/chat/fn -s -d text=東京の気温を教えてください。 | jq
```

```json
{
  "content": "東京の気温は10.0度です。",
  "properties": {
    "role": "ASSISTANT"
  },
  "messageType": "ASSISTANT"
}
```

### マルチモーダル（画像）

マルチモーダルに対応したGPT-4oを使用して画像を説明させます。

```sh
curl localhost:8080/image-caption -s -d url="https://www.gravatar.com/avatar/e107c65b007e7abb6b2e53054428fb5a" | jq
```

> [!WARNING]
> ちなみにタイミングが悪かった（？）のか、この文章を書いているときは高頻度でChat Completion APIに対してタイムアウトが発生していました。
> タイムアウトが発生しても何度か実行するとそのうち成功するはず……！

結果は次の通りです（「やけどぐ」に笑った）。

```json
{
  "messageType": "ASSISTANT",
  "media": [],
  "metadata": {
    "finishReason": "STOP",
    "role": "ASSISTANT",
    "id": "chatcmpl-9ZOjFAxExx6mX3exAuO7lAHfR0R6p",
    "messageType": "ASSISTANT"
  },
  "content": "この画像は、雪だるまのイラストです。雪だるまは、黒い目と口、赤い鼻、赤いマフラーをしています。体には青いボタンが3つ描かれています。また、雪だるまの右手には「やけどぐ」という日本語の文字が書かれたオレンジ色の札を持っています。"
}
```

## その他の情報

- `org.springframework.ai.autoconfigure.openai.OpenAiChatProperties`を見たところ、デフォルトの言語モデルは~~`gpt-3.5-turbo`~~`gpt-4o`みたい
- `spring-boot-docker-compose`を入れたのに起動時に`spring.datasource.url`が無いと言われた。動的に設定されるんじゃなかったっけ？
- `org.springframework.ai.evaluation.BasicEvaluationTest`を使えば生成された回答の評価ができるみたい
    - [Ragas](https://docs.ragas.io/)のようなものになっていくのかなー
