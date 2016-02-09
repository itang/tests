// #[lang = "eq"]
// #[stable(feature = "rust1", since = "1.0.0")]
// pub trait PartialEq<Rhs: ?Sized = Self> {
// This method tests for `self` and `other` values to be equal, and is used
// by `==`.
// #[stable(feature = "rust1", since = "1.0.0")]
// fn eq(&self, other: &Rhs) -> bool;
//
// This method tests for `!=`.
// #[inline]
// #[stable(feature = "rust1", since = "1.0.0")]
// fn ne(&self, other: &Rhs) -> bool { !self.eq(other) }
// }
//

#[derive(PartialEq, Debug)]
struct User {
    name: String,
}

fn main() {
    let u1 = User { name: "itang".to_owned() };
    let u2 = User { name: "itang".to_owned() };

    assert_eq!(u1, u2);
}
