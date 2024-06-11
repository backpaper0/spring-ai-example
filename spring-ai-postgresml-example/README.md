# Spring AI PostgresML example

[PostgresML](https://postgresml.org/)を用いた埋め込みを試します。

## 起動

```sh
mvn spring-boot:run
```

> ![NOTE]
> PostgresMLの起動が遅くHikariCPの構築時にデータベースへの認証が通らずに失敗することがあります。
> その場合はあらかじめ`docker compose up -d`でPostgresMLを起動してから`mvn spring-boot:run`するようにしてください。

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
