#!/bin/bash

tail -f ../test-tail-log/logs/access.log | java -jar target/scala-2.11/storm-kestrel-assembly-1.0-SNAPSHOT.jar
