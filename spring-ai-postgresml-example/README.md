# Spring AI PostgresML example

[PostgresML](https://postgresml.org/)を用いた埋め込みを試します。

## 起動

```sh
mvn spring-boot:run
```

## 実行

### 埋め込み

次のコマンドを実行してください。

```sh
curl localhost:8080/embedding -s -d text=Hello
```

次のような結果が得られます。

```
[0.06964111328125,-0.058785971254110336,0.0048672654666006565,...(中略)...,0.08991798758506775]
```

## その他の情報

- `org.springframework.ai.autoconfigure.postgresml.PostgresMlAutoConfiguration`が動いてくれないので明示的に`EmbeddingClient`をbean定義しています
    - conditionのどれかがマッチしなかったのだろうか？
    - でも`logging.level.org.springframework.boot=debug`にして出力されたログを見ても`PostgresMlAutoConfiguration`の名前がどこにもない
    - 謎
