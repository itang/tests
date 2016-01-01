/**
 * Hello, 2016.
 */
struct I(i32);

trait Times<T> {
    fn times<F>(&self, block: F) where F: Fn(T);
}

impl Times<i32> for I {
    fn times<F>(&self, block: F)
        where F: Fn(i32)
    {
        for i in 0..self.0 {
            block(i);
        }
    }
}

fn main() {
    let i = I(100);
    i.times(|x| println!("[{}]Hello, 2016!", x + 1));
}
