use test2::times_two;

pub fn add_three_times_four(x: int) -> int {
  times_four(add_three(x))
}

fn add_three(x: int) -> int { 
  test2::add_one(test2::add_one(test2::add_one(x)))
}

fn times_four(x: int) -> int { times_two(times_two(x)) }

#[cfg(test)]
mod test {
  // Sub-modules are allowed to 'see' private functions in the parent
  use super::add_three;
  use super::times_four;

  #[test]
  fn test_add_three() {
    let result = add_three(5i);
    assert_eq!(8i, result);
  }

  #[test]
  fn test_times_four() {
    let result = times_four(5i);
    assert_eq!(20i, result);

    //explicitly 
    assert_eq!(32i, super::times_four(8i));
  }

  #[test]
  fn test_mod_fn() {
    assert_eq!(2, super::test2::add_one(1));
  }
}

mod test2 {
  pub fn add_one(x: int) -> int {
    x + 1
  }

  pub fn times_two(x: int) -> int { x * 2 }
}