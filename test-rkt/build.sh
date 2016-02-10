#!/bin/bash

#1.4
#CGO_ENABLED=0 GOOS=linux go build -o hello -a -installsuffix cgo .

#1.5
CGO_ENABLED=0 GOOS=linux go build -o testrkt -a -tags netgo -ldflags '-w' .
