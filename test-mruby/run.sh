git clone git@github.com:mruby/mruby.git --depth 1
(cd mruby;make)

gcc -Imruby/src -Imruby/include -c test_program.c -o test_program.o
gcc -o test_program test_program.o mruby/build/host/lib/libmruby.a -lm

./test_program
