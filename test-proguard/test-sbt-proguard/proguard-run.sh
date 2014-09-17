#!/bin/bash

TARGET_JAR=target/scala-2.11/proguard/test-sbt-proguard_2.11-1.0-SNAPSHOT.jar

sbt 'proguard:proguard'

du -h $TARGET_JAR

java -cp $TARGET_JAR Main
