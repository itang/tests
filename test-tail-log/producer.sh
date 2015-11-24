#!/bin/bash

tail -f logs/access.log | scala src/producer.scala
