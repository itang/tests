#[cfg(test)]
mod test {
    #[test]
    fn it_works() {}
}

extern crate libc;

use libc::{c_void, c_int};

#[repr(C)]
pub struct JNINativeInterface {
    reserver0: *mut c_void,
    reserved1: *mut c_void,
    reserved2: *mut c_void,
    reserved3: *mut c_void,
}

pub type JNIEnv = *const JNINativeInterface;

#[no_mangle]
pub extern "C" fn Java_Adder_add(jre: *mut JNIEnv,
                                 class: *const c_void,
                                 v1: c_int,
                                 v2: c_int)
                                 -> c_int {
    v1 + v2
}
