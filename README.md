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
