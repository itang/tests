# Test Hazelcast

## Usage

```
$ sbt 'runMain test_hazelcast.server'
...
Members [1] {
    Member [172.17.42.1]:5701 this
}
...
$ sbt 'runMain test_hazelcast.server'
Members [2] {
    Member [172.17.42.1]:5701
    Member [172.17.42.1]:5702 this
}

$ sbt 'runMain test_hazelcast.client'
Members [2] {
    Member [172.17.42.1]:5701
    Member [172.17.42.1]:5702
}

$ sbt 'runMain test_hazelcast.client'
k=1, v=Joe
k=3, v=Avi
k=2, v=Ali

```
