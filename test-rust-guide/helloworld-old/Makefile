files=\
	main.rs

prepare:
	mkdir -p target

compile: $(files)
		rustc main.rs --out-dir=target

build: prepare compile

run: build
	./target/main

clean: 
	rm -rf target
