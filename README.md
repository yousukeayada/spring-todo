## 概要
- Spring Boot を使った簡易的な Todo 管理アプリ。
- API としても使える。

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

# jar ファイルにビルドして実行してもよい
./gradlew build
java -jar build/libs/< jar ファイル名>
```

### GUI
- http://localhost:8080 にアクセス

### API
```bash
curl -X GET http://localhost:8080/api/todos
curl -X GET http://localhost:8080/api/todos/{id}
curl -X POST -H 'Content-Type:application/json' -d '{"todo":"hoge"}' http://localhost:8080/api/todos
curl -X PUT -H 'Content-Type:application/json' -d '{"todo":"hoge"}' http://localhost:8080/api/todos/{id}
curl -X DELETE http://localhost:8080/api/todos/{id}
```