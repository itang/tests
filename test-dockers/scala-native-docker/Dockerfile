FROM ubuntu:14.04

RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak
COPY source.list /etc/apt/sources.list

RUN apt-get update
RUN apt-get install -y apt-transport-https


RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823

RUN apt-get update
RUN apt-get install -y sbt libgc-dev openjdk-7-jdk git
RUN git clone https://github.com/scala-native/scala-native.git --depth 1

COPY repositories .sbt/repositories

WORKDIR scala-native
RUN sbt 'rtlib/publishLocal'
RUN sbt 'nscplugin/publishLocal'

RUN sbt package
