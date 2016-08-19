#![feature(custom_derive, plugin)]
#![plugin(serde_macros)]

extern crate serde;
extern crate serde_json;

use serde_json::Map;


#[derive(Debug, PartialEq, Serialize, Deserialize)]
struct Point {
    x: f64,
    y: f64,
}

fn main() {
    let mut map = Map::new();
    map.insert("x".to_string(), 1.0);
    map.insert("y".to_string(), 2.0);

    let s = serde_json::to_string(&map).unwrap();
    println!("json: {}", s);

    let deserialized_map: Map<String, f64> = serde_json::from_str(&s).unwrap();
    assert_eq!(map, deserialized_map);

    let point = Point { x: 1.0, y: 2.0 };
    let s = serde_json::to_string(&point).unwrap();
    println!("json: {}", s);

    let deserialized_point: Point = serde_json::from_str(&s).unwrap();

    assert_eq!(point, deserialized_point);

    println!("point: {:?}", deserialized_point);
}
