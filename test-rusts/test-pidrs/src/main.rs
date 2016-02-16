extern crate libc;

fn pid() -> i32 {
    unsafe { libc::getpid() }
}

fn main() {
    println!("current process id:{}", pid());
}
