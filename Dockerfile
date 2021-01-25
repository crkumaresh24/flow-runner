FROM centos
RUN yum -y update && yum install -y unzip && yum install -y java-1.8.0-openjdk-devel
ENV JAVA_HOME=/usr/lib/jvm/java
ARG JAR_FILE=build/libs/*
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["/usr/lib/jvm/java/bin/java","-jar","/app.jar"]