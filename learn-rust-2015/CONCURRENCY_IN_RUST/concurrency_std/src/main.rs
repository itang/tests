use std::thread;
use std::sync::Arc;
use std::sync::Mutex;

fn main() {
    let v = Arc::new(Mutex::new(vec![1, 2, 3, 4, 5]));

    let mut handles = vec![];

    for i in 0..5 {
        let mutex = v.clone();
        let handle = thread::spawn(move || {
            let mut v = mutex.lock().unwrap();

            v[i] = v[i] + 1;
            println!("{:?}", v[i]);
        });
        handles.push(handle);
    }

    for handle in handles {
        handle.join().unwrap();
    }
}

fn _test2() {
    // let v = vec![1,2];
    //
    // let handle = thread::spawn(||{
    // println!("{:?}", &v); // closure may outlive the current function, but
    // it borrows `v`, which is owned by the current function
    // });
    // handle.join().unwrap();
}
