//
// An array is a collection of objects of the same type T, stored in contiguous memory. Arrays are created using brackets [],
// and their size, which is konow at compile time, is part of their signature [T; size].
//
// Slices are similar to arrays, but their size is not known at compile time. Instend, a slice is a two-word object, the first word
// is a pointer to the data, and the second word is the length of the slice. Slices can be used to borrow a section of an array, and have the type signature &[T].
//

use std::mem;

// This function borrows a slice.
fn analyze_slice(slice: &[i32]) {
    println!("first element of the slice:{}", slice[0]);
    println!("the slice has {} elements", slice.len());
}

fn main() {
    // Fixed-size array (type signature is superfluous)
    let xs: [i32; 5] = [1, 2, 3, 4, 5];
    // All elements can be initialized to the same value.
    let ys: [i32; 500] = [0; 500];
    assert_eq!(xs[0], 1);

    assert_eq!(ys[499], 0);

    assert_eq!(xs.len(), 5);
    assert_eq!(ys.len(), 500);

    println!("array occupies {} bytes", mem::size_of_val(&xs));
    assert_eq!(4 * 5, mem::size_of_val(&xs));

    analyze_slice(&xs);

    analyze_slice(&ys[1..4]);

    fn copy_or_move() {
        fn swap_head_last(slice: &mut [i32]) {
            let length = slice.len();
            if length < 2 {
                return;
            }
            let first = slice[0];
            slice[0] = slice[length - 1];
            slice[length - 1] = first;
        }

        let mut xs = [1, 2, 3];
        swap_head_last(&mut xs);
        assert_eq!(xs[0], 3);
        assert_eq!(xs[2], 1);

        // Copy
        let ys = xs;
        println!("xs after copy: {:?}", xs);
        println!("ys: {:?}", ys);
    }
    copy_or_move();
}

#[test]
#[should_panic]
fn it_works() {
    // check on runtime: index out of bounds: the len is 500 but the index is 500
    let ys: [i32; 500] = [0; 500];
    println!("{}", ys[500]);
}
