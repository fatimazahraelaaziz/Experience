FROM openjdk:11

# Update and install necessary packages
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-11-jdk \
    openssl \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java

# Set default command and entrypoint
CMD ["echo", "No default command specified"]
ENTRYPOINT ["echo", "No entrypoint specified"]

# Copy log4j2.properties file
COPY integrationcontroller/src/main/resources/log4j2.properties /bin/log4j2.properties

# Copy IntegrationController JAR
ADD integrationcontroller/target/IntegrationController-1.0-SNAPSHOT.jar /app/Controller.jar

# Copy producer JAR
COPY produceri3s/target/i3s-1.0-SNAPSHOT.jar /app/producer.jar

# Copy variableconsumer JAR
COPY variableconsumer/target/variableconsumer-1.0-SNAPSHOT.jar /app/consumer.jar
