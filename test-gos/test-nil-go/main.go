package main

import "fmt"

type User struct {
	name string
}

func main() {
	//structs
	var user User
	fmt.Printf("user == User{}: %v\n", user == User{})

	//pointer
	var a *int
	fmt.Printf("a == nil: %v\n", a == nil)
	a = new(int)
	fmt.Printf("after a = new(int);a == nil: %v\n", a == nil)

	var up *User
	fmt.Printf("up == nil: %v\n", up == nil)
	up = &user
	fmt.Printf("after up = &user, up == nil: %v\n", up == nil)

	var ai *interface{}
	fmt.Printf("ai == nil: %v\n", ai == nil)

	// ai = &user // cannot use &user (type *User) as type *interface {} in assignment: *interface {} is pointer to interface, not interface
	// fmt.Printf("after ai = &user, ai == nil: %v\n", ai == nil)

	// function
	var fa func(int) int
	fmt.Printf("var fa func(int) int;fa == nil : %v\n", fa == nil)

	fa = func(i int) int { return i + 1 }
	fmt.Printf("after fa = func(i int) int { return i + 1 }; fa == nil: %v\n", fa == nil)

	// channel
	var ca chan string
	fmt.Printf("ca == nil: %v\n", ca == nil)

	ca = make(chan string)
	fmt.Printf("after ca = make(chan string); ca == nil: %v\n", ca == nil)
	go func() {
		ca <- "hello"
	}()

	msg := <-ca
	fmt.Printf("msg: %v\n", msg)

	// interface
	var ia interface{}
	fmt.Printf("ia == nil:%v\n", ia == nil)

	ia = 1
	fmt.Printf("after ia = 1; ia == nil: %v\n", ia == nil)

	var p1 *int
	ia = p1
	fmt.Printf("after ia = p1; ia == nil : %v\n", ia == nil) // false

	// map
	var m1 map[string]int
	fmt.Printf("m1 == nil: %v\n", m1 == nil)

	m1 = make(map[string]int)
	fmt.Printf("after m1 == make(map[string]int), m1 == nil: %v\n", m1 == nil)

	// slice
	var s1 []int
	fmt.Printf("s1 == nil: %v\n", s1 == nil)

	s1 = make([]int, 3)
	fmt.Printf("after make, s1 == nil: %v\n", s1 == nil)

	var arr1 [5]int = [5]int{1, 2, 3, 4, 5}
	s1 = arr1[1:5]
	fmt.Printf("s1.len: %v\n", len(s1))
}
