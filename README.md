## Import Database

create postgres database and import sql file in `sql/postgres.sql`

## Config Kafka

enable `listeners=PLAINTEXT://:9092` in kafka `server.properties` file

```Shell
...
listeners=PLAINTEXT://:9092
...
```

then you can run zookeper and kafka on separate terminal

```Shell
cd to kafka folder
sh bin/zookeeper-server-start.sh config/zookeeper.properties
```

```Shell
cd to kafka folder
sh bin/kafka-server-start.sh config/server.properties
```

## Config Sring boot

please config the following setting in `application.properties` before running the app:

```Shell
spring.datasource.url=YOUR_POSTGRESQL_URL
spring.datasource.username=YOUR_POSTGRESQL_USER
spring.datasource.password=YOUR_POSTGRESQL_PASSWORD

spring.data.mongodb.uri=YOUR_MONGODB_URL

spring.kafka.consumer.bootstrap-servers=YOUR_KAFKA_SERVER
spring.kafka.producer.bootstrap-servers=YOUR_KAFKA_SERVER
```

make sure all services is running, then you can start the backend application through your favorite IDE such as Intellij IDEA, Eclipse etc.

## Config React

change the baseURL in `src/config/axios.js` to your backend URL

install node package and run:

```Shell
npm install
npm start
```

## Postman Collection

You can import postman collection from `Postman/collection.json` to your postman and don't forget to add `URL` variable in your environtment.
