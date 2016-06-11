FROM ubuntu:14.04

ENV REFRESHED_AT 2014-06-01

RUN apt-get update

RUN apt-get -y install redis-server redis-tools

EXPOSE 6397

ENTRYPOINT ["/usr/bin/redis-server"]

CMD []
