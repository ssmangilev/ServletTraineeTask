# Используйте официальный образ PostgreSQL для инициализации базы данных
FROM postgres:latest as db

# Копируйте SQL-скрипт в директорию для инициализации базы данных
COPY init.sql /docker-entrypoint-initdb.d/

# Используйте официальный образ Tomcat
FROM tomcat:9.0.65-jdk8

# Копируйте ваш war-файл в папку Tomcat webapps
COPY target/ServletTraineeTask.war /usr/local/tomcat/webapps/

# Добавьте PostgreSQL JDBC драйвер
COPY --from=db /usr/local /usr/local

# Опционально, установите переменные окружения для подключения к базе данных
ENV DB_URL=jdbc:postgresql://postgres-container:5432/postgress
ENV DB_USER=admin
ENV DB_PASSWORD=admin
ENV POSTGRES_HOST_AUTH_METHOD=trust