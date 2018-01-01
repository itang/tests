extern crate hlua;

use hlua::Lua;
use std::path::Path;
use std::fs::File;

fn main() {
    let lua = Lua::new();
    let lua2 = Lua::new();
    let mut vs = vec![lua, lua2];
    test(&mut vs);
}

fn test(luavms: &mut Vec<Lua>) {
    for lua in luavms {
        lua.openlibs();

        lua.set("x", 2);
        lua.execute::<()>("x = x + 1").unwrap();
        let x: i32 = lua.get("x").unwrap();
        println!("{}", x);

        lua.execute::<()>("print('hello')").unwrap();
        let x: i32 = lua.execute_from_reader::<i32, File>(File::open(&Path::new("script.lua")).unwrap()).unwrap();
        println!("{}", x)
    }
}
