# Spring AI + Ollama EXAMPLE

## 使用する日本語モデル

- [cyberagent/DeepSeek-R1-Distill-Qwen-32B-Japanese](https://huggingface.co/cyberagent/DeepSeek-R1-Distill-Qwen-32B-Japanese)をggufフォーマットへ変換した[mmnga/cyberagent-DeepSeek-R1-Distill-Qwen-32B-Japanese-gguf](https://huggingface.co/mmnga/cyberagent-DeepSeek-R1-Distill-Qwen-32B-Japanese-gguf)

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

```
<think>
ユーザーが日本語で深く考えてほしいとリクエストしていますね。まず、Spring Bootの基本的な特徴を整理する必要があります。軽量フレームワークであること、デフォルト設定が揃っていること、簡単に開発できる点などが挙げられます。

次に、ユーザーの要望である「100文字程度」でまとめる必要があります。冗長な表現を避けつつ、重要なキーワードを入れる工夫が必要です。例えば、「迅速」「軽量」「デフォルト設定」などですね。

また、Spring Bootが何を目的として作られたのかを考えると、開発の効率化や統合されたエコシステムの提供がポイントになります。これらを簡潔に表現する必要があります。

さらに、ユーザーがどのような背景で質問しているのか推測します。プログラミング初心者なのか、既にSpring Frameworkを知っている人かによって説明の角度が変わります。ここでは一般的な知識を前提に、具体的な特徴を列挙しつつ短くまとめることが求められています。

最後に、文字数を確認しながら情報を優先順位で整理します。「軽量フレームワーク」「デフォルト設定」「開発効率化」など重要な要素を組み合わせて、自然な日本語の文を作成します。
</think>

Spring BootはJava向け軽量フレームワーク。デフォルト設定・統合ツールが揃い、迅速にアプリ開発可能なエコシステムを提供する。自動配置機能で複雑な設定不要で、Spring Communityとの連携も容易だ。
```

## 参考

- https://docs.spring.io/spring-ai/reference/1.0/api/chat/ollama-chat.html
