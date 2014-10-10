# test-cassandra

测试Apache Cassandra， 使用clojurewerkz/cassaforte库

## Prepare

1. install Cassandra

  see <https://github.com/itang/bin/blob/master/install-cassandra.sh>
  
2. start server

```
$ cd $CASSANDRA_HOME
$ bin/cassandra
```

## Usage

### run test
    $ lein run
    
### results

```
$ cd $CASSANDRA_HOME
    ➜  apache-cassandra-2.1.0  bin/cqlsh
Connected to Test Cluster at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 2.1.0 | CQL spec 3.2.0 | Native protocol v3]
Use HELP for help.
cqlsh> use test_keyspace;
cqlsh:test_keyspace> select * from users;

 name   | age | city
--------+-----+---------------
 Robert |  25 |        Berlin
   Alex |  19 |        Munich
    Sam |  21 | San Francisco

(3 rows)
```

## Options



## Examples


### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2014 itang

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
