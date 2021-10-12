FROM maven:3-jdk-8-alpine as MVN_BUILD

COPY . /app/ferry/code/

RUN cd /app/ferry/code/ && mvn clean package -Dmaven.test.skip=true

FROM openjdk:8-alpine

WORKDIR /app/ferry/

COPY --from=MVN_BUILD /app/ferry/ /app/ferry/

RUN apk add --no-cache ca-certificates tzdata

ENV TZ=Asia/Shanghai

EXPOSE 8080
