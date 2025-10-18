# --- 1단계: 빌드(Build) 스테이지 ---
# Java 17 Gradle 이미지를 사용하여 'builder'라는 별명을 붙입니다.
FROM gradle:jdk17-jammy AS builder

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app

# 먼저 의존성 관련 파일만 복사합니다.
COPY build.gradle settings.gradle ./

# 소스 코드를 복사합니다.
COPY src ./src

# Gradle을 사용하여 애플리케이션을 빌드합니다. (-x test는 테스트를 건너뛰는 옵션)
# 이 명령어가 성공하면 build/libs/*.jar 파일이 생성됩니다.
RUN gradle build -x test --no-daemon


# --- 2단계: 실행(Run) 스테이지 ---
# 더 가벼운 Eclipse Temurin 이미지를 최종 베이스로 사용합니다.
FROM eclipse-temurin:17-jre-jammy

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app

# 1단계(builder)에서 생성된 .jar 파일을 현재 컨테이너로 복사합니다.
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너가 시작될 때 "java -jar /app/app.jar" 명령어를 실행합니다.
ENTRYPOINT ["java","-jar","/app/app.jar"]