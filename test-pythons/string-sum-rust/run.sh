#!/bin/bash

cargo build --release

cp target/release/libstring_sum.so string_sum.so
python test.py
