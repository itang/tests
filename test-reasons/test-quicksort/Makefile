build:
	jbuilder build @install

run: build
	jbuilder exec test

test:
	jbuilder runtest

pin:
	opam pin add .

repin: build
	opam upgrade test

build-all:
	jbuilder build --workspace jbuild-workspace.dev @install

test-all:
	jbuilder build --workspace jbuild-workspace.dev @runtest

.PHONY: build test pin repin build-all test-all