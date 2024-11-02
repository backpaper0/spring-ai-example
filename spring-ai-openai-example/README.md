# Spring AI OpenAI example

## 準備

`spring.ai.openai.api-key`プロパティへOpenAIのAPIキーを設定してください。

## 起動

```sh
mvn spring-boot:run
```

## 実行

### Chat Completions API

[Chat Completions API](https://platform.openai.com/docs/api-reference/chat)によるテキスト生成を試します。

次のコマンドを実行してください。

```sh
curl localhost:8080/chat -s -d text="Spring Bootについて100文字程度で説明してください 。" | jq
```

次のような結果が得られます。

```json
{
  "output": {
    "messageType": "ASSISTANT",
    "metadata": {
      "finishReason": "STOP",
      "refusal": "",
      "index": 0,
      "role": "ASSISTANT",
      "id": "chatcmpl-AP5wuWCsYuWPHNElKjC9ss6QXt6wd",
      "messageType": "ASSISTANT"
    },
    "toolCalls": [],
    "content": "Spring Bootは、Javaでのアプリケーション開発を簡略化するフレームワークです。設定を自動化し、最小限の設定で実行可能なスタンドアローンアプリケーションを迅速に立ち上げることができます。"
  },
  "metadata": {
    "finishReason": "STOP",
    "contentFilterMetadata": null
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

data:ど

data:の

data:よう

data:に

data:お

data:手

data:伝

data:い

data:できます

data:か

data:？
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
  "messageType": "ASSISTANT",
  "metadata": {
    "refusal": "",
    "finishReason": "STOP",
    "index": 0,
    "id": "chatcmpl-AP6N92LiVVXuAKSXZGFLqJWzFTeh7",
    "role": "ASSISTANT",
    "messageType": "ASSISTANT"
  },
  "toolCalls": [],
  "content": "大阪の現在の気温は8.0度です。"
}
```

```sh
curl localhost:8080/chat/fn -s -d text=東京の気温を教えてください。 | jq
```

```json
{
  "messageType": "ASSISTANT",
  "metadata": {
    "refusal": "",
    "finishReason": "STOP",
    "index": 0,
    "id": "chatcmpl-AP6NUbNveJBfz2NaIxST3nK9GVzNf",
    "role": "ASSISTANT",
    "messageType": "ASSISTANT"
  },
  "toolCalls": [],
  "content": "東京の現在の気温は10度です。"
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

結果は次の通りです（「やけど」に笑った）。

```json
{
  "messageType": "ASSISTANT",
  "metadata": {
    "refusal": "",
    "finishReason": "STOP",
    "index": 0,
    "id": "chatcmpl-AP6NpQterfZqPDYyKNJmfWMv6nG16",
    "role": "ASSISTANT",
    "messageType": "ASSISTANT"
  },
  "toolCalls": [],
  "content": "この画像には、雪だるまが描かれています。雪だるまは、黒い目とオレンジ色の鼻、赤い口を持ち、赤いマフラーをしています。胴体には青いボタンが3つあります。右手に「やけど」(やけど注意)と書かれた札を持っています。"
}
```

## その他の情報

- `org.springframework.ai.autoconfigure.openai.OpenAiChatProperties`を見たところ、デフォルトの言語モデルは~~`gpt-3.5-turbo`~~`gpt-4o`みたい
- `spring-boot-docker-compose`を入れたのに起動時に`spring.datasource.url`が無いと言われた。動的に設定されるんじゃなかったっけ？
- `org.springframework.ai.evaluation.BasicEvaluationTest`を使えば生成された回答の評価ができるみたい
    - [Ragas](https://docs.ragas.io/)のようなものになっていくのかなー
