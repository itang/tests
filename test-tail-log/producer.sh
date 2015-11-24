#!/bin/bash

tail -f access.log | scala src/producer.scala
