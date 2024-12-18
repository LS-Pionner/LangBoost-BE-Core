FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY build/libs/Memoria-0.0.1-SNAPSHOT.jar /app/Core.jar
# 환경 변수 파일 복사
COPY src/main/resources/application.yml /app/application.yml
COPY src/main/resources/application-dev.yml /app/application-dev.yml

# 컨테이너에서 사용할 포트를 노출
EXPOSE 8082

# 애플리케이션 실행
CMD ["java", "-jar", "/app/Core.jar"]
