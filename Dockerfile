FROM openjdk:11
##dockerfile
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

# Copy producer JAR
COPY produceri3s/target/i3s-1.0-SNAPSHOT.jar /app/producer.jar

#COPY ./scripts/ /bin
##COPY restructurebinpack/src/main/resources/log4j2.properties /bin/log4j2.properties

##ADD restructurebinpack/target/restructure-1.0-SNAPSHOT.jar /app/Controller.jar
#CMD ["java", "DEBUG_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" , "-jar" , "/Controller-1.0-SNAPSHOT.jar"]
# ENTRYPOINT [ "sh", "-c", "java $DEBUG_OPTIONS -jar /restructure-1.0-SNAPSHOT.jar" 

# Copy variableconsumer JAR
COPY variableconsumer/target/variableconsumer-1.0-SNAPSHOT.jar /app/consumer.jar

# Copy log4j2.properties file
COPY integrationcontroller/src/main/resources/log4j2.properties /bin/log4j2.properties
# Copy IntegrationController JAR
ADD integrationcontroller/target/IntegrationController-1.0-SNAPSHOT.jar /app/Controller.jar


## COPY ./scripts/ /bin
# COPY graphcontroller2/src/main/resources/log4j2.properties /bin/log4j2.properties
# ADD graphcontroller2/target/graphcontroller2-1.0-SNAPSHOT.jar /app/Controller.jar
# ENTRYPOINT [ "sh", "-c", "java $DEBUG_OPTIONS -jar /graphcontroller2-1.0-SNAPSHOT.jar" ]



# Copy multipleConsumers JAR
# COPY multipleConsumers/target/multipleConsumers-1.0-SNAPSHOT.jar /app/consumer.jar






