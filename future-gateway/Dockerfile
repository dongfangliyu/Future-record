FROM java:8
MAINTAINER zhangyajun <dongfangliyu@gmail.com>

VOLUME /tmp

ADD /target/future-gateway-*.jar /app.jar

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]


echo "stop and delete container"
CID=$(docker ps -a | grep "jenkins-test" | awk '{print $1}')
if [ -n "$CID" ]; then
echo "has container，CID=$CID"
docker stop $CID
docker rm $CID
fi
echo "delete image"
DID=$(docker images | grep "jenkins-test" | awk '{print $1}')
if [ -n "$DID" ]; then
docker rmi jenkins-test
fi
echo "build docker image"
mvn dockerfile:build
echo "current docker images："
docker images | grep jenkins-test
echo "start container----->"
docker run -d -p 2020:2020 --name jenkins-test jenkins-test
echo "start success！"
————————————————
版权声明：本文为CSDN博主「唥雨凊」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_44777669/article/details/110789051