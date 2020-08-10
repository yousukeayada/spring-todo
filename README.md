## 概要
Spring Boot を使った簡易的な Todo リスト管理アプリ。

## 使い方
- MySQL 起動
- ユーザと DB 作成
```bash
mysql.server start
mysql -uroot -p
# 以降 mysql 内の作業
create database todo;
create user springtodo identified by "pass";
grant all on todo.* to "springtodo"@"%";
```

- 実行
```bash
./gradlew bootRun
```

- http://localhost:8080 にアクセス