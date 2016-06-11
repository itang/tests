FROM ubuntu:14.04

ENV REFRESHED_AT 2014-06-01

RUN apt-get update -qq && apt-get install -qqy curl
RUN curl -fsSL https://get.docker.com/ | sh

RUN apt-get install -qqy iptables ca-certificates git

RUN apt-get install -qqy openjdk-7-jdk

ENV JENKINS_HOME /opt/jenkins/data
ENV JENKINS_MIRROR http://mirrors.jenkins-ci.org

RUN mkdir -p $JENKINS_HOME/plugins

RUN curl -sf -o /opt/jenkins/jenkins.war -L $JENKINS_MIRROR/war-stable/latest/jenkins.war

RUN for plugin in chunchnorris greenballs scm-api git-client git ws-cleanup ;\
    do curl -sf -o $JENKINS_HOME/plugins/${plugins}.hpi \
      -L $JENKINS_MIRROR/plugins/${plugin}/latest/${plugin}.hpi ; done

COPY ./dockerjenkins.sh /usr/local/bin/dockerjenkins.sh

RUN chmod +x /usr/local/bin/dockerjenkins.sh

VOLUME /var/lib/docker

EXPOSE 8080

ENTRYPOINT ["/usr/local/bin/dockerjenkins.sh"]
