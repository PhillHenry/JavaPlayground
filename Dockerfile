FROM ghcr.io/openzipkin/alpine:3.19.0

# docker build -t javaplayground:latest -f ./Dockerfile .

RUN apk add --no-cache tini

ARG java_home=/usr/lib/jvm/java-21-openjdk
ENV JAVA_HOME=$java_home
ENV PATH=${JAVA_HOME}/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

ADD target/classes /opt/

WORKDIR /opt/

ENTRYPOINT [ "java", "-cp", "/opt/", "uk.co.odinconsultants.interrupts.PausingMain",  "/bin/sleep",  "10000" ]

