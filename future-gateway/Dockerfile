FROM java:8
MAINTAINER zhangyajun <dongfangliyu@gmail.com>

VOLUME /tmp

ADD /future-gateway/target/future-gateway-*.jar /app.jar

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]