$ make run
sbt 'proguard:proguard'
[info] Loading global plugins from /home/itang/.sbt/0.13/plugins
[info] Loading project definition from /home/itang/my/tests/test-proguard/test-sbt-proguard/project
[info] Set current project to test-sbt-proguard (in build file:/home/itang/my/tests/test-proguard/test-sbt-proguard/)
[success] Total time: 0 s, completed Sep 17, 2014 3:07:23 PM
cat target/scala-2.11/proguard/configuration.pro
-injars "/home/itang/my/tests/test-proguard/test-sbt-proguard/target/scala-2.11/classes"(!META-INF/MANIFEST.MF)
-injars "/home/itang/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.11.2.jar"(!META-INF/MANIFEST.MF)
-injars "/home/itang/.ivy2/cache/me.itang/scatang_2.11/jars/scatang_2.11-0.1.jar"(!META-INF/MANIFEST.MF)
-libraryjars "/home/itang/dev-env/jdk1.7.0_67/jre/lib/rt.jar"
-outjars "/home/itang/my/tests/test-proguard/test-sbt-proguard/target/scala-2.11/proguard/test-sbt-proguard_2.11-1.0-SNAPSHOT.jar"
-dontnote
-dontwarn
-ignorewarnings
-keep public class Main {
    public static void main(java.lang.String[]);
}
du -h target/scala-2.11/proguard/test-sbt-proguard_2.11-1.0-SNAPSHOT.jar
228K  target/scala-2.11/proguard/test-sbt-proguard_2.11-1.0-SNAPSHOT.jar
java -cp target/scala-2.11/proguard/test-sbt-proguard_2.11-1.0-SNAPSHOT.jar Main 
Hello, World!
Hello, World!
Hello, World!
Hello, World!
Hello, World!
Hello, World!
