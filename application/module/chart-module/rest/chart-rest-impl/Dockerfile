FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080

COPY target/overlay /
WORKDIR "/opt/app"
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
CMD ["java", "-classpath", ".", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]