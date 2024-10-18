# Используем базовый образ OpenJDK
FROM openjdk:23-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл в контейнер
COPY target/DTO-1.0-SNAPSHOT.jar dto.jar

# Указываем порт, который будет использовать приложение
EXPOSE 8083

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "dto.jar"]


