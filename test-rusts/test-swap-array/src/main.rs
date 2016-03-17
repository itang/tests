fn main() {
    let mut a = [1, 2, 3];
    swap_array(&mut a, 0, 0);
    println!("a:{:?}", a);
}

fn swap_array<T: Copy>(arr: &mut [T], a: usize, b: usize) {
    let max_len = arr.len();
    assert!(a < max_len && b < max_len);

    if a == b {
        return;
    }

    let tmp = arr[a];
    arr[a] = arr[b];
    arr[b] = tmp;
}
