FROM openjdk:17
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
CMD ["./gradlew", "clean", "bootJar"]
COPY ./build/libs/* ./osm.content.jar
EXPOSE 8090
CMD ["java","-jar", "-Dspring.profiles.active=docker","osm.content.jar"]