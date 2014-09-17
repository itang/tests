files=\
	src/main/scala/main.scala

TARGET_JAR=target/scala-2.11/proguard/test-sbt-proguard_2.11-1.0-SNAPSHOT.jar
CONF=target/scala-2.11/proguard/configuration.pro

default: run

proguard: $(files)
	sbt 'proguard:proguard'

run: proguard
	cat $(CONF)
	
	du -h $(TARGET_JAR)

	java -cp $(TARGET_JAR) Main 
