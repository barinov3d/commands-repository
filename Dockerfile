FROM maven:3.6-openjdk-11
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./pom.xml $PROJECT_DIR
#RUN mvn dependency:resolve
ADD ./src/ $PROJECT_DIR/src
RUN mvn -B clean install -DskipTests -Dcheckstyle.skip
#RUN mvn install

FROM openjdk:11-jre-slim
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/commands-repository* $PROJECT_DIR/
EXPOSE 8080
CMD ["java", "-jar", "/opt/project/commands-repository-1.0-SNAPSHOT.jar"]

#docker build -t my-library:1.0 ./