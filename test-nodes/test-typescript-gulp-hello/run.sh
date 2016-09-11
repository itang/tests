#!/bin/bash

function run() {
    gulp && node dist/main.js
}

case $1 in
    run) run;;
    *)   echo "Usage: ./run.sh run | web"; run;;
esac;
