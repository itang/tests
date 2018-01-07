#!/bin/bash

## 并行执行(par sub shell process)
(
cat > a.txt <<- EOF
  1
EOF

sleep 1
) &

(
cat >> a.txt << EOF
  2
EOF

sleep 1
) &

wait

cat a.txt
