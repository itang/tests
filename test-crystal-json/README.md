# Test Crystal JSON

  Compare with http://serdardogruyol.com/rust-vs-ruby-building-an-api/

## Usage

$ rake run

## Benchmarks

$ wrk -d30 -t2 -c100 http://localhost:3000/

```

Running 30s test @ http://localhost:3000/
 2 threads and 100 connections
 Thread Stats   Avg      Stdev     Max   +/- Stdev
   Latency     7.15ms    1.99ms  22.63ms   76.34%
   Req/Sec     6.79k     1.13k   12.06k    71.80%
 394610 requests in 30.00s, 40.27MB read
 Socket errors: connect 0, read 394608, write 0, timeout 0
Requests/sec:  13153.91
Transfer/sec:      1.34MB
```
