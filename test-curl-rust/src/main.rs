extern crate curl;

use curl::http;

fn main() {
  let resp = http::handle()
  .get("http://www.example.com")
  .exec().unwrap();

  println!("code={};\nheaders={};", 
    resp.get_code(),
    resp.get_headers(),);

  __spe();

  let body = String::from_utf8_lossy(resp.get_body());
  println!("\nbody;\n{}", body);
}

fn __spe() {
  println!("{}", "_".repeat(80));
}
