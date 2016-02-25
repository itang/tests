fn main() {
    let bytes: &[u8] = str_to_slice("hello 你好");
    {
        let s: &str = slice_to_str(bytes);
        println!("slice_to_str:{}", s);
    }

    let v: Vec<u8> = slice_to_vec(bytes);
    let s: String = vec_to_string(&v);

    println!("s:{}", s);

    {
        let _: &[u8] = vec_to_slice(&v);
    }
}

fn str_to_slice(s: &str) -> &[u8] {
    s.as_bytes()
}

fn slice_to_str(s: &[u8]) -> &str {
    unsafe { std::str::from_utf8_unchecked(s) }
}

fn slice_to_vec(slice: &[u8]) -> Vec<u8> {
    let mut v: Vec<u8> = vec![];
    v.extend_from_slice(slice); // push_all

    v
}

fn vec_to_string(v: &Vec<u8>) -> String {
    // String::from_utf8 use move value
    // String::from_utf8(*v).unwrap(); // rust can't move out of borrowed content
    String::from_utf8(v.clone()).unwrap()
}

fn vec_to_slice<'a>(v: &'a Vec<u8>) -> &'a [u8] {
    v.as_slice()
}
