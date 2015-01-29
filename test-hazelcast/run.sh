#!/bin/bash

case "$1" in
  server) sbt 'runMain test_hazelcast.server';;
  client) sbt 'runMain test_hazelcast.client';;
  *) echo "./run.sh server|client";;
esac
