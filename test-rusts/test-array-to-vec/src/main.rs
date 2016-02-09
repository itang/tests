fn main() {
    let a = [1, 2, 3];
    let v: Vec<i32> = a.iter()
                       .cloned()
                       .collect();

    let doubled: Vec<i32> = a.iter()
                             .map(|&x| x * 2)
                             .collect();

    assert_eq!(vec![1, 2, 3], v);
    assert_eq!(vec![2, 4, 6], doubled);
}
