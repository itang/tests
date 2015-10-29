# test jnr-ffi

@see https://github.com/jnr/jnr-ffi

## Prepare

cp libjffi-1.2.so to load library path:

* /lib64/
* /usr/lib64/
* classpath: /jni/x86_64-Linux/
* classpath: jin/x86_64-Linux/
* /jni/x86_64-Linux/
* /usr/lib/
* /lib/
* /usr/java/packages/lib/amd64/

```
sh 'mkdir -p src/main/resources/jni/x86_64-Linux'
sh 'cp $JRUBY_HOME/lib/jni/x86_64-Linux/libjffi-1.2.so src/main/resources/jni/x86_64-Linux'
```

## Run

```
$ (cd ../fibonacci; cargo build --release)

$ export JRUBY_HOME=/home/itang/.rbenv/versions/jruby-9.0.1.0
$ rake prepare && rake run
```
