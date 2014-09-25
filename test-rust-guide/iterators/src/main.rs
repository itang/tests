fn main() {
  // The range function returns an iterator
  for x in range(0i, 10i) {
    println!("{:d}", x);
  }

  let mut range1 = range(0i, 10i);
  loop {
    match range1.next() {
      Some(x) => println!("{}", x),
      None => { break }
    }
  }

  let nums = vec![1i, 2i, 3i];

  for num in nums.iter() {
    println!("{}", num);
  }

  for num in nums.iter() {
    println!("{:d}", *num);
  }

  for &num in nums.iter() {
    println!("{:d}", num);
  }

  // Consumers
  let one_to_one_hundred = range(0i, 100i).collect::<Vec<int>>();
  println!("one_to_one_hundred: {}", one_to_one_hundred);

  let greater_than_forty_two = range(0i, 100i).find(|x| *x >= 42);
  match greater_than_forty_two {
    Some(x) => println!("We got some numbers!: {}", x),
    None    => println!("No numbers found :("),
  }

  let greater_than_forty_two = range(0i, 100i).find(|&x| x >= 90);
  match greater_than_forty_two {
    Some(x) => println!("We got some numbers!: {}", x),
    None    => println!("No numbers found :("),
  }

  let sum = range(1i, 100i)
              .fold(0i, |sum, x| sum + x);
  println!("some is: {}", sum);


  let nums = [1i, 2i, 3i];

  for num in nums.iter() {
    println!("{}", num);
  }

  for x in range(1i, 10i).map(|x| x + 1i) {
    println!("x is : {}", x);
  }

  for i in std::iter::count(1i, 5i).take(5) {
    println!("{}", i);
  }

  for i in range(1i, 10i).filter(|x| x % 2 == 0) {
    println!("{}", i);
  }

  let r = range(1i, 1000i)
    .filter(|x| x % 2 == 0)
    .filter(|x| x % 3 == 0)
    .take(5)
    .collect::<Vec<int>>();
  println!("r is :{}", r);
}
