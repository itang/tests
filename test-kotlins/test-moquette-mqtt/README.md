# test moquette mqtt

## prepare

### local install mqtt-drpc

```
$ git clone https://github.com/SkPhilipp/mqtt-drpc.git

$ cd mqtt-drpc

$ mvn clean install
```

## usage

run server

```
$ gradle run
```

run client1(publish)

```
$ gradle run_client1
```

run client2(subscibe)

```
$ gradle run_client2
```

run client3(listener disconnnect event)

```
$ gradle run_client2
```

run client4(async)

```
$ gradle run_client4
```

## references

- https://github.com/andsel/moquette

- https://github.com/fusesource/mqtt-client

- http://blog.csdn.net/zhu_tianwei/article/details/42983867
