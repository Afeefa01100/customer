FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/customer.jar /app/customer.jar
EXPOSE 3322
ENTRYPOINT ["java","-jar","/app/customer.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]