use std::mem;
use std::thread;


const SIZE_FN: usize = 1024 * 1020 * 8; // ~8M
const SIZE_TH: usize = 1024 * 1020 * 2; // ~2M
const SIZE_OT: usize = 1024 * 1024 * 6; // 100M for heap

fn main() {
    let r0 = 1i32;
    let r = "hello".to_string();
    let a = 12i32;

    let s = S { a: 10, b: 20 };
    let b: i32 = 1;

    let s2 = S2 { a: 10 };
    println!("r0 {:p}", &r0);
    println!("r {:p}", &r);
    println!("a {:p}", &a);
    println!("s {:p}", &s);
    println!("b {:p}", &b);
    println!("s2 {:p}", &s2);

    println!("S sizeof: {}", mem::size_of::<S>());

    test_fn_stack_size();
    test_thread_stack_size();

    println!("try test heap...");
    test_heap();
}

fn test_heap() {
    println!("Heap happy???");
    // @see:
    // stackoverflow.com/questions/30242770/how-to-allocate-arrays-on-the-heap-in-rust-1-0-beta
    // ERROR
    // let _it = Box::new(S_HEAP{a: Box::new([0; SIZE_OT])});
    // ERROR
    use std::iter::repeat;
    const SIZE: usize = SIZE_OT;
    let _v: Vec<_> = repeat(10.0).take(SIZE).collect();
}

fn test_fn_stack_size() {
    println!("tip: Default function stack size: 8M?");

    S_FN { a: [0; SIZE_FN] };
}

fn test_thread_stack_size() {
    let t = thread::spawn(|| {
        println!("tip: Default thread stack size: 2M?");
        // 8M == 2097152 B
        S_TH { a: [0; SIZE_TH] };
    });
    t.join().unwrap();
}

struct S {
    a: i64,
    b: i64,
}

struct S2 {
    a: i32,
}

struct S_FN {
    a: [i8; SIZE_FN],
}


struct S_TH {
    a: [i8; SIZE_TH],
}

struct S_HEAP {
    a: Box<[i8; SIZE_OT]>, /* The problem is that the array is being passed
                            * to the Box::new function as an argument,
                            * which means it has to be created first,
                            * which means it has to be created on the stack */
}
