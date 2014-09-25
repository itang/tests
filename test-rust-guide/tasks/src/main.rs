use std::sync::Future;
use std::task;
use std::rand;

fn main() {
  spawn(proc(){
    println!("Hello from a task!");
  });

  let mut x = vec![1i, 2i, 3i];

  spawn(proc() {
    println!("The value of x[0] is: {}", x[0]);
  });

  //println!("The value of x[0] is: {}", x[0]); // error: use of moved value: `x`


  let (tx, rx) = channel();
  spawn(proc () {
    tx.send("Hello from a task too!".to_string());
  });

  let message = rx.recv();
  println!("{}", message);

  let (tx1, rx1) = channel();
  let (tx2, rx2) = channel();

  spawn(proc() {
    tx1.send("Hello from a task!".to_string());
    let message = rx2.recv();
    println!("{}", message);
  });

  let message = rx1.recv();
  println!("{}", message);

  tx2.send("Goodbye from main!".to_string());


  //24.1 Futures
  let mut delayed_value = Future::spawn(proc(){
    12345i
  });

  println!("value = {}", delayed_value.get());

  // 24.2 Success and failure
  spawn(proc() {
    fail!("Nope.");
  });

  println!("here!");

  let result = task::try(proc() {
    if rand::random() {
      println!("ok");
    }else{
      fail!("oops!");
    }
  });
  match result  {
    Ok(..) => println!("ret: ok"),
    Err(..) =>  println!("ret: error"),
  }
}
