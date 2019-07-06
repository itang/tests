#!/bin/bash

docker run --rm -p 7890:7890 \
  -v "$PWD/goaccess/data:/srv/data"         \
  -v "$PWD/goaccess/html:/srv/report"       \
  -v "$PWD/logs:/srv/logs"           \
  --name=goaccess allinurl/goaccess