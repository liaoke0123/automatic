FROM frolvlad/alpine-oraclejdk8:slim
ADD automatic-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -Xms256m -Xmx256m  -jar /app.jar" ]