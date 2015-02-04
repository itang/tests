package main

import "github.com/flosch/pongo2"
import "github.com/Unknwon/macaron"
import "runtime"

//import "fmt"

func main() {
	runtime.GOMAXPROCS(4)

	// Compile the template first (i. e. creating the AST)
	tpl, err := pongo2.FromString("Hello {{ name|capfirst }}!")
	if err != nil {
		panic(err)
	}

	m := macaron.Classic()

	m.Get("/", func() string {
		return "Hello world!"
	})

	m.Get("/tpl", func() string {
		out, err := tpl.Execute(pongo2.Context{"name": "florian"})
		if err != nil {
			panic(err)
		}
		return out
	})
	m.Run()
}
