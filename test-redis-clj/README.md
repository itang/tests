# test-redis-clj

## Usage

```
 sudo docker run -p 6379:6379 --name some-redis -d redis
```

```
$ lein run
```
```
$ curl -i `curl -X POST http://localhost:8080/http://www.deftype.com`
```

```
HTTP/1.1 302 Found
Location: http://www.deftype.com
Content-Length: 0
Server: http-kit
Date: Sun, 24 Aug 2014 01:49:08 GMT
```

## License

Copyright © 2014 itang

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
