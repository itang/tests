# test scala native with rust

## Env

### build myrust.so

```
$ cd myrust
$ cargo build
```

## run

```
$ LD_LIBRARY_PATH=./myrust/target/debug/:$LD_LIBRARY_PATH sbt run

or

$ LD_LIBRARY_PATH=./myrust/target/debug/:$LD_LIBRARY_PATH ./target/scala-2.13/scala-native-seed-project-out
```