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

## Config React

change the baseURL in `src/config/axios.js` to your backend URL

install node package and run:

```Shell
npm install
npm start
```

## Postman Collection

You can import postman collection from `Postman/collection.json` to your postman and don't forget to add `URL` environtment.
