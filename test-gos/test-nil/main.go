package main

import (
	"fmt"
	"reflect"
)

// Fooer is just a dummy interface to implement
type Fooer interface {
	IsFoo() bool
}

// Foo is our example type
type Foo struct{}

// IsFoo implements the Fooer interface
func (f *Foo) IsFoo() bool {
	return true
}

// NewFoo returns an uninitialized Foo pointer
func NewFoo() *Foo {
	var VarFooPointer *Foo
	return VarFooPointer
}

// NewFooNil foo returns nil as Foo pointer
func NewFooNil() *Foo {
	return nil
}

// NewFooer returns an uninitialized Foo pointer as Fooer
func NewFooer() Fooer {
	var VarFooPointer *Foo
	return VarFooPointer
}

// NewFooerNil returns nil as Fooer
func NewFooerNil() Fooer {
	return nil
}

var q = fmt.Printf // shorthand to reduce noise

func main() {
	// 指针的初始值是nil
	var VarFooPointer *Foo
	q("Q01  VarFooPointer == nil?       %t \t value = %#v reflect.IsNil = %t\n",
		VarFooPointer == nil,
		VarFooPointer,
		reflect.ValueOf(VarFooPointer).IsNil())

	VarFooPointer = NewFoo()
	q("Q02  VarFooPointer == nil?       %t \t value = %#v reflect.IsNil = %t\n",
		VarFooPointer == nil,
		VarFooPointer,
		reflect.ValueOf(VarFooPointer).IsNil())

	VarFooPointer = NewFooNil()
	q("Q03  VarFooPointer == nil?       %t \t value = %#v reflect.IsNil = %t\n",
		VarFooPointer == nil,
		VarFooPointer,
		reflect.ValueOf(VarFooPointer).IsNil())

	var VarEmptyInterface interface{}
	q("Q04  VarEmptyInterface == nil?   %t \t value = %#v reflect.IsNil = %s\n",
		VarEmptyInterface == nil,
		VarEmptyInterface,
		"panics")

	VarEmptyInterface = VarFooPointer
	q("Q05  VarEmptyInterface == nil?   %t \t value = %#v reflect.IsNil = %t\n",
		VarEmptyInterface == nil,
		VarEmptyInterface,
		reflect.ValueOf(VarEmptyInterface).IsNil())

	VarEmptyInterface = NewFoo()
	q("Q06  VarEmptyInterface == nil?   %t \t value = %#v reflect.IsNil = %t\n",
		VarEmptyInterface == nil,
		VarEmptyInterface,
		reflect.ValueOf(VarEmptyInterface).IsNil())

	VarEmptyInterface = NewFooNil()
	q("Q07  VarEmptyInterface == nil?   %t \t value = %#v reflect.IsNil = %t\n",
		VarEmptyInterface == nil,
		VarEmptyInterface,
		reflect.ValueOf(VarEmptyInterface).IsNil())

	var VarFooAsserted = VarEmptyInterface.(*Foo)
	q("Q08  VarFooAsserted == nil?      %t \t value = %#v reflect.IsNil = %t\n",
		VarFooAsserted == nil,
		VarFooAsserted,
		reflect.ValueOf(VarFooAsserted).IsNil())

	var VarFooerAsserted = VarEmptyInterface.(Fooer)
	q("Q09  VarFooerAsserted == nil?    %t \t value = %#v reflect.IsNil = %t\n",
		VarFooerAsserted == nil,
		VarFooerAsserted,
		reflect.ValueOf(VarFooerAsserted).IsNil())

	var VarFooer = NewFooer()
	q("Q10  VarFooer == nil?            %t \t value = %#v reflect.IsNil = %t\n",
		VarFooer == nil,
		VarFooer,
		reflect.ValueOf(VarFooer).IsNil())

	var VarFooer2 = NewFooerNil()
	q("Q11  VarFooer2 == nil?           %t \t value = %#v reflect.IsNil = %s\n",
		VarFooer2 == nil,
		VarFooer2,
		"panics")

	var VarEmptyInterface2 interface{}
	VarEmptyInterface2 = VarEmptyInterface.(*Foo)
	q("Q12  VarEmptyInterface2 == nil?  %t \t value = %#v reflect.IsNil = %t\n",
		VarEmptyInterface2 == nil,
		VarEmptyInterface2,
		reflect.ValueOf(VarEmptyInterface2).IsNil())

	VarEmptyInterface2 = NewFooer()
	q("Q13  VarEmptyInterface2 == nil?  %t \t value = %#v reflect.IsNil = %t\n",
		VarEmptyInterface2 == nil,
		VarEmptyInterface2,
		reflect.ValueOf(VarEmptyInterface2).IsNil())

	VarEmptyInterface2 = NewFooerNil()
	q("Q14  VarEmptyInterface2 == nil?  %t \t value = %#v reflect.IsNil = %s\n",
		VarEmptyInterface2 == nil,
		VarEmptyInterface2,
		"panics")

	var VarFooer3 Fooer
	VarFooer3 = VarEmptyInterface.(*Foo)
	q("Q15  VarFooer3 == nil?           %t \t value = %#v reflect.IsNil = %t\n",
		VarFooer3 == nil,
		VarFooer3,
		reflect.ValueOf(VarFooer3).IsNil())

	VarFooer3 = NewFoo()
	q("Q16  VarFooer3 == nil?           %t \t value = %#v reflect.IsNil = %t\n",
		VarFooer3 == nil,
		VarFooer3,
		reflect.ValueOf(VarFooer3).IsNil())

	VarFooer3 = NewFooNil()
	q("Q17  VarFooer3 == nil?           %t \t value = %#v reflect.IsNil = %t\n",
		VarFooer3 == nil,
		VarFooer3,
		reflect.ValueOf(VarFooer3).IsNil())

	VarEmptyInterface = nil
	q("Q18  VarEmptyInterface == nil?   %t \t value = %#v reflect.IsNil = %s\n",
		VarEmptyInterface == nil,
		VarEmptyInterface,
		"panics")
}
