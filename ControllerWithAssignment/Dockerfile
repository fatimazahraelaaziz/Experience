FROM centos:7

RUN yum -y update && yum -y install java-11-openjdk-headless openssl && yum -y clean all

# Set JAVA_HOME env var
ENV JAVA_HOME /usr/lib/jvm/java

ARG version=latest
ENV VERSION ${version}
ENV DEBUG_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"


COPY ./src/main/resources/log4j2.properties /bin/log4j2.properties

ADD target/controllerassign-1.0-SNAPSHOT.jar /



ENTRYPOINT [ "sh", "-c", "java $DEBUG_OPTIONS -jar /controllerassign-1.0-SNAPSHOT.jar " ]

