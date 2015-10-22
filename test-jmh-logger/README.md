$ sbt jmh:run -i 3 -wi 3 -f1 -t1

```
[info] Benchmark                                                Mode  Cnt          Score          Error  Units
[info] JMHSample_01_HelloWorld.get_logger_by_method_prototype  thrpt    3   89030540.563 ±  1827767.292  ops/s
[info] JMHSample_01_HelloWorld.get_logger_by_scala             thrpt    3  605880325.427 ± 18964250.928  ops/s
[info] JMHSample_01_HelloWorld.get_logger_by_static            thrpt    3  842402858.439 ±  8502999.506  ops/s
```
