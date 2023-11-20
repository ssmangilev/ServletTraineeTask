#!/bin/bash

# Пересборка проекта с использованием Maven
mvn clean package

# Ожидание завершения процесса mvn
while pgrep -x mvn > /dev/null; do
    sleep 1
done

# Остановка Docker Compose, если он уже запущен
docker-compose down

# Запуск Docker Compose
docker-compose up -d