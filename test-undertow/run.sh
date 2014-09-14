#!/bin/bash

case "$1" in
  server) sbt 'run-main test_undertow.Server';;
  *) sbt 'run-main test_undertow.Client';;
esac
