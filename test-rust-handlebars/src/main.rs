extern crate rustc_serialize;
extern crate handlebars;

use rustc_serialize::json::{Json, ToJson};
use std::collections::BTreeMap;

use handlebars::Handlebars;

struct Person {
    name: String,
    age: i16,
}

impl ToJson for Person {
    fn to_json(&self) -> Json {
        let mut m: BTreeMap<String, Json> = BTreeMap::new();
        m.insert("name".to_string(), self.name.to_json());
        m.insert("age".to_string(), self.age.to_json());
        m.to_json()
    }
}

fn main() {
    let source = "Hello, {{name}}";
    let mut handlebars = Handlebars::new();
    handlebars.register_template_string("hello", source.to_string()).ok().unwrap();

    let data = Person {
        name: "Ning Sun".to_string(),
        age: 32767
    };

    let result = handlebars.render("hello", &data);

    println!("{:?}", result);
}
