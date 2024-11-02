# Spring AIでRAG

Spring AIで[LangChainのLCELクックブックのRAG](https://python.langchain.com/docs/expression_language/cookbook/retrieval)をなぞってみます。

## 準備

`spring.ai.openai.api-key`プロパティへOpenAIのAPIキーを設定してください。

## 起動

```sh
mvn spring-boot:run
```

## 実行

### シンプルなRAG

```bash
curl localhost:8080/rag1 -s -d question="where did harrison work?"
```

```
Harrison worked at Kensho.
```

### 会話履歴

```bash
curl localhost:8080/rag2 -s -d question="where did harrison work?"
```

```
Harrison worked at Kensho.
```

```bash
curl localhost:8080/rag2 -s -d question="where did he work?" -d chatHistory="Human: Who wrote this notebook?" -d chatHistory="AI: Harrison"
```

```
Harrison worked at Kensho.
```

### チャンキング

```bash
mvn spring-boot:run -Dspring-boot.run.main-class=com.example.chunking.App
```

チャンクは`target/documents.jsonl`へ出力されます。
