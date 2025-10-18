    # 1. 베이스 이미지 선택: Eclipse Temurin 버전 17 JDK를 포함한 이미지를 사용합니다.
    FROM eclipse-temurin:17-jdk-jammy

    # 2. 작업 디렉토리 설정: 컨테이너 내부에 /app 이라는 폴더를 만듭니다.
    WORKDIR /app

    # 3. 빌드된 .jar 파일을 컨테이너로 복사:
    # 로컬 컴퓨터의 build/libs/ 폴더에 있는 .jar 파일을 컨테이너의 /app 폴더로 복사하고, 이름을 app.jar로 변경합니다.
    COPY build/libs/*.jar app.jar

    # 4. 애플리케이션 실행 명령어:
    # 컨테이너가 시작될 때 "java -jar /app/app.jar" 명령어를 실행합니다.
    ENTRYPOINT ["java", "-jar", "/app/app.jar"]
    
