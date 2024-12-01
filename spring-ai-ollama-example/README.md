# Spring AI + Ollama EXAMPLE

## 使用する日本語モデル

- [Llama 3 ELYZA JP 8B](https://huggingface.co/elyza/Llama-3-ELYZA-JP-8B-GGUF)

## 起動

```bash
ollama serve
```

```bash
mvn spring-boot:run
```

モデルがない場合は自動でダウンロードします。

## 実行

```bash
curl localhost:8080/chat -d query="Spring Bootを100文字程度で説明して。"
```

> Spring Bootは、JavaベースのWebアプリケーションフレームワークです。起動するだけで基本的な設定が自動的に行われるため、開発生産性が高まります。依存関係管理やプロパティファイルを使用した設定を不要にし、簡単なコードで始めることができます。Spring Bootは、Webアプリケーション開発の入門者から上級者まで幅広く利用されています。

## 参考

- https://docs.spring.io/spring-ai/reference/1.0/api/chat/ollama-chat.html
