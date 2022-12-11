#!/bin/bash

echo "spring.jpa.hibernate.ddl-auto=create" > application.properties
echo "spring.datasource.url=jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME" >> application.properties

echo "spring.datasource.username=$MYSQL_USER" >> application.properties
echo "spring.datasource.password=$MYSQL_PASSWORD" >> application.properties

echo "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver" >> application.properties


echo "Waiting for the MySQL server to start on $DB_HOST:$DB_PORT"

while ! nc -z $DB_HOST $DB_PORT; do
  sleep 0.2
done

echo "MySQL Server has started"

java -jar app.jar