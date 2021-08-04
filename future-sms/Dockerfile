FROM java:8
MAINTAINER zhangyajun <dongfangliyu@gmail.com>

VOLUME /tmp

ADD /target/future-sms-*.jar /app.jar

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
