use deno::*;

fn init(context: &mut dyn PluginInitContext) {
    context.register_op("hello", Box::new(op_hello));
}

init_fn!(init);

pub fn op_hello(data: &[u8], _zero_copy: Option<PinnedBuf>) -> CoreOp {
    let mut hello_str = "Hello".to_owned();
    if data.len() > 0 {
        hello_str.push_str(" ");
        hello_str.push_str(std::str::from_utf8(data).unwrap());
    }
    let buf: Buf = hello_str.into_boxed_str().into_boxed_bytes();
    Op::Sync(buf)
}
