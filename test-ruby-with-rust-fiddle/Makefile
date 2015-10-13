files=\
	line.rs

build: $(files)
	rustc line.rs --crate-type=dylib

run: build
	ruby main.rb
