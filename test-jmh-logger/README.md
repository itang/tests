$ sbt 'jmh:run -i 3 -wi 3 -f1 -t1'

```
[info] Benchmark                                                Mode  Cnt          Score           Error  Units
[info] JMHSample_01_HelloWorld.get_logger_by_method_cache      thrpt    4  182513442.618 ±   2052142.474  ops/s
[info] JMHSample_01_HelloWorld.get_logger_by_method_prototype  thrpt    4   91830722.751 ±   2909817.261  ops/s
[info] JMHSample_01_HelloWorld.get_logger_by_scala             thrpt    4  583077002.867 ± 124259547.328  ops/s
[info] JMHSample_01_HelloWorld.get_logger_by_static            thrpt    4  837523731.777 ±  39608565.331  ops/s
```
