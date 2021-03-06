## 概要
- Spring Boot を使った簡易的な Todo 管理アプリ。
  - ログイン認証あり。
  - 参照、追加、編集、削除を行える。
- API としても使える。
  - Discord に毎日12時に Todo を通知してくれる Bot を作成した。

### スクリーンショット
![メイン画面](screenshots/main-screen.png)
![Discord画面](screenshots/discord-screen.png)

### 環境
- Spring Boot 2.3.2
- Java 11
- MySQL 8.0.19

### DB 設計
- todolist
```bash
mysql> desc todolist;
+----------+--------------+------+-----+---------+-------+
| Field    | Type         | Null | Key | Default | Extra |
+----------+--------------+------+-----+---------+-------+
| id       | int          | NO   | PRI | NULL    |       |
| deadline | date         | YES  |     | NULL    |       |
| todo     | varchar(255) | NO   |     | NULL    |       |
+----------+--------------+------+-----+---------+-------+
3 rows in set (0.01 sec)
```
- user
```bash
mysql> desc user;
+----------+--------------+------+-----+---------+-------+
| Field    | Type         | Null | Key | Default | Extra |
+----------+--------------+------+-----+---------+-------+
| id       | bigint       | NO   | PRI | NULL    |       |
| password | varchar(255) | YES  |     | NULL    |       |
| username | varchar(255) | YES  |     | NULL    |       |
+----------+--------------+------+-----+---------+-------+
3 rows in set (0.00 sec)
```

## 使い方
### ローカルで MySQL を使う場合
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

- `application.properties` に以下を記載。
```java
spring.datasource.url=jdbc:mysql://localhost:3306/todo?serverTimezone=JST
spring.datasource.username=springtodo
spring.datasource.password=pass
spring.jpa.hibernate.ddl-auto=update
```

### Heroku の ClearDB を使う場合
```bash
heroku config:set CLEARDB_DATABASE_URL='mysql://<ユーザ名>:<パスワード>@<ホスト名>/<DB名>?reconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8&serverTimezone=JST'
export CLEARDB_DATABASE_URL=$(heroku config:get CLEARDB_DATABASE_URL)
```

- `application.properties` に以下を記載。
```java
spring.datasource.url=jdbc:${CLEARDB_DATABASE_URL}
spring.jpa.hibernate.ddl-auto=update
```

- 実行
```bash
./gradlew bootRun

# jar ファイルにビルドして実行してもよい
./gradlew build
java -jar build/libs/< jar ファイル名>
```

### ログイン
- 認証のためユーザデータを予め登録しておく。
- プログラムを実行するとテーブル「user」が自動で作成される。
```bash
# mysql 内の操作
insert into user values(1,"pass","test");
```

### GUI
- http://localhost:8080 にアクセスすると、ログイン画面にリダイレクトされる。
  - username: test
  - password: pass

### API
|HTTP メソッド|URI|機能|
|---|---|---|
|GET|/api/todos|全ての todo を取得する。|
|GET|/api/todos/{id}|一つの todo を取得する。|
|POST|/api/todos|todo を新規登録する。|
|PUT|/api/todos/{id}|todo を更新する。|
|DELETE|/api/todos/{id}|todo を削除する。|

- Cookie を保存。
```bash
curl -i -c cookie.txt -X POST -d "username=<ユーザ名>" -d "password=<パスワード>" localhost:8080/sign_in
```
- 以降保存した Cookie を使用する。
```bash
curl -X GET http://localhost:8080/api/todos -b cookie.txt
curl -X GET http://localhost:8080/api/todos/{id}
curl -X POST -H 'Content-Type:application/json' -d '{"todo":"hoge", "deadline":"2020-08-01"}' http://localhost:8080/api/todos
curl -X PUT -H 'Content-Type:application/json' -d '{"todo":"hoge"}' http://localhost:8080/api/todos/{id}
curl -X DELETE http://localhost:8080/api/todos/{id}
```