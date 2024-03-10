# Spring AIでRAG

[Spring AIでLangChainのクックブックにあるRAGをなぞる](https://zenn.dev/backpaper0/articles/ee37fd39c8feff)のソースコードです。

## 準備

`spring.ai.openai.api-key`プロパティへOpenAIのAPIキーを設定してください。

## 起動

```sh
mvn spring-boot:run
```

## 実行

```bash
curl localhost:8080/rag -s -d question="where did harrison work?"
```

```
Harrison worked at Kensho.
```
