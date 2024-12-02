# spring-ai-ollama-openai-compatibility-example

OllamaのREST APIサーバーはOpenAI互換を備えようとしています。

- 参考: https://github.com/ollama/ollama/blob/main/docs/openai.md

これは`spring-ai-openai-spring-boot-starter`でOllamaを使う例です。

## 準備

```bash
ollama pull hf.co/elyza/Llama-3-ELYZA-JP-8B-GGUF
```

## 起動

```bash
ollama serve
```

```bash
mvn spring-boot:run
```

## 動作確認

```bash
curl localhost:8080/chat -d query="Spring Bootを100文字程度で簡潔に説明して。"
```

```json
{
  "metadata": {
    "finishReason": "STOP",
    "contentFilterMetadata": null
  },
  "output": {
    "messageType": "ASSISTANT",
    "metadata": {
      "finishReason": "STOP",
      "refusal": "",
      "index": 0,
      "role": "ASSISTANT",
      "id": "chatcmpl-253",
      "messageType": "ASSISTANT"
    },
    "toolCalls": [],
    "content": " Spring Bootは、Javaのフレームワークです。Webアプリケーションを開発する際に使用します。Spring Frameworkを基盤としています。Spring Bootを使用することで、依存性注入(DI)やAOP(Aspect-Oriented Programming)などの機能を簡単に使うことができます。また、設定ファイルの自動生成やテンプレートエンジンも備わっており、開発効率が高まります。"
  }
}
```
