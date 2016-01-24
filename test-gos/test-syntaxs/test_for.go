package main

import "fmt"

type A struct {
	i int
}

func (a *A) next() bool {
	a.i++
	return a.i < 100
}

func main() {
	a := A{0}
	for a.next() {
		fmt.Println(a.i)
	}
}
