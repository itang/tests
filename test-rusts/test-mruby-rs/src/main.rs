#[macro_use]
extern crate mrusty;

use mrusty::*;


struct Cont {
    value: i32,
}

fn main() {
    println!("Hello, world!");

    let mruby = Mruby::new();
    mrclass!(Cont, "Container", {
        def!("initialize", |v: i32| Cont { value: v });

        def!("value", |mruby, slf: Cont| mruby.fixnum(slf.value));
    });

    // Add file to the context, making it requirable.
    mruby.def_file::<Cont>("cont");

    // Add spec testing.
    describe!(Cont,
              "
  context 'when containing 1' do
    it 'returns 1 when calling #value' do
       expect(Container.new(1).value).to eql 1
    end
  end
    ");

    let result = mruby.run("require 'cont'
        (0 .. Container.new(3).value).map {|x| x + 1}.reduce {|x,y| x + y }
    ")
                      .unwrap();

    println!("{}", result.to_i32().unwrap());
}
