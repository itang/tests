#[no_mangle]
pub extern fn fibonacci(n: i32) -> i32 {
    match n {
        1 => 1,
        2 => 1,
        _ => fibonacci(n - 1) + fibonacci(n - 2)
    }
}

#[test]
fn one() {
    assert_eq!(1, fibonacci(1));
}

#[test]
fn two() {
    assert_eq!(1, fibonacci(2));
}
#[test]
fn three() {
    assert_eq!(2, fibonacci(3));
}
#[test]
fn four() {
    assert_eq!(3, fibonacci(4));
}
#[test]
fn five() {
    assert_eq!(5, fibonacci(5));
}
#[test]
fn six() {
    assert_eq!(8, fibonacci(6));
}
