mod greetings;

#[path = "greetings2.rs"]
mod g;

mod greetings3;

fn main() {
  greetings::say_hello();
  g::say_hello();
  
  greetings3::say_hello();

  greetings3::foo::foo_function();
  greetings3::bar::bar_function();

  greetings3::foo_function();
}