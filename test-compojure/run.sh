#!/bin/sh

export LEIN_JAVA_CMD=
java -Xms512m -Xmx1g -cp `lein classpath` clojure.main -m test-compojure.core
