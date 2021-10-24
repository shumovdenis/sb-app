FROM openjdk:11
EXPOSE 8080
ADD target/sb-app-0.0.1-SNAPSHOT.jar devapp.jar
ENTRYPOINT ["java","-jar","devapp.jar"]