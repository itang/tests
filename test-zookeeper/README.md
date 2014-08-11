# test zookeeper

https://github.com/itang/todo.itang.me/issues/37

## $ zkServer.sh start
JMX enabled by default
Using config: /home/itang/dev-env/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED

## $ sbt run

已经触发了WatchedEvent state:SyncConnected type:None path:null事件！
get /testRootPath:testRootData
get_children /testRootPath: [testChildPathOne]
目录节点状态：[2,2,1407741906619,1407741906619,0,1,0,0,12,1,3
]
已经触发了WatchedEvent state:SyncConnected type:NodeChildrenChanged path:/testRootPath事件！
testChildDataTwo
/testRootPath
----/testRootPath/testChildPathTwo
++++/testRootPath/testChildPathOne
--------/testRootPath/testChildPathOne/test
/testWorks
----/testWorks/worker0000000001
----/testWorks/worker0000000000
----/testWorks/worker0000000002
已经触发了WatchedEvent state:SyncConnected type:NodeDeleted path:/testRootPath/testChildPathTwo事件！
已经触发了WatchedEvent state:SyncConnected type:NodeDeleted path:/testRootPath事件！
[success] Total time: 8 s, completed Aug 11, 2014 3:25:07 PM

## $ zkCli.sh

Connecting to localhost:2181
2014-08-11 15:25:19,076 [myid:] - INFO  [main:Environment@100] - Client environment:zookeeper.version=3.4.6-1569965, built on 02/20/2014 09:09 GMT
2014-08-11 15:25:19,090 [myid:] - INFO  [main:Environment@100] - Client environment:host.name=itang-HP
2014-08-11 15:25:19,091 [myid:] - INFO  [main:Environment@100] - Client environment:java.version=1.7.0_55
2014-08-11 15:25:19,126 [myid:] - INFO  [main:Environment@100] - Client environment:java.vendor=Oracle Corporation
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:java.home=/home/itang/dev-env/jdk1.7.0_55/jre
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:java.class.path=/home/itang/dev-env/zookeeper/bin/../build/classes:/home/itang/dev-env/zookeeper/bin/../build/lib/*.jar:/home/itang/dev-env/zookeeper/bin/../lib/slf4j-log4j12-1.6.1.jar:/home/itang/dev-env/zookeeper/bin/../lib/slf4j-api-1.6.1.jar:/home/itang/dev-env/zookeeper/bin/../lib/netty-3.7.0.Final.jar:/home/itang/dev-env/zookeeper/bin/../lib/log4j-1.2.16.jar:/home/itang/dev-env/zookeeper/bin/../lib/jline-0.9.94.jar:/home/itang/dev-env/zookeeper/bin/../zookeeper-3.4.6.jar:/home/itang/dev-env/zookeeper/bin/../src/java/lib/*.jar:/home/itang/dev-env/zookeeper/bin/../conf:
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:java.library.path=/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:java.io.tmpdir=/tmp
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:java.compiler=<NA>
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:os.name=Linux
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:os.arch=amd64
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:os.version=3.13.0-32-generic
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:user.name=itang
2014-08-11 15:25:19,127 [myid:] - INFO  [main:Environment@100] - Client environment:user.home=/home/itang
2014-08-11 15:25:19,128 [myid:] - INFO  [main:Environment@100] - Client environment:user.dir=/home/itang/my/tests/test-zookeeper
2014-08-11 15:25:19,129 [myid:] - INFO  [main:ZooKeeper@438] - Initiating client connection, connectString=localhost:2181 sessionTimeout=30000 watcher=org.apache.zookeeper.ZooKeeperMain$MyWatcher@30250179
Welcome to ZooKeeper!
2014-08-11 15:25:19,244 [myid:] - INFO  [main-SendThread(localhost:2181):ClientCnxn$SendThread@975] - Opening socket connection to server localhost/127.0.0.1:2181. Will not attempt to authenticate using SASL (unknown error)
2014-08-11 15:25:19,256 [myid:] - INFO  [main-SendThread(localhost:2181):ClientCnxn$SendThread@852] - Socket connection established to localhost/127.0.0.1:2181, initiating session
JLine support is enabled
2014-08-11 15:25:19,335 [myid:] - INFO  [main-SendThread(localhost:2181):ClientCnxn$SendThread@1235] - Session establishment complete on server localhost/127.0.0.1:2181, sessionid = 0x147c3f444d90001, negotiated timeout = 30000

WATCHER::

WatchedEvent state:SyncConnected type:None path:null
[zk: localhost:2181(CONNECTED) 0] ls /
[test1, zookeeper]
[zk: localhost:2181(CONNECTED) 1] 

