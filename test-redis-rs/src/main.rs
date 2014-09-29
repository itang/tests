extern crate redis;

fn main() {
  let client = redis::Client::open("redis://127.0.0.1/").unwrap();
  let conn = client.get_connection().unwrap();
  redis::cmd("SET").arg("my_key").arg(42i).execute(&conn);
  assert_eq!(Ok(42i), redis::cmd("GET").arg("my_key").query(&conn));
}
