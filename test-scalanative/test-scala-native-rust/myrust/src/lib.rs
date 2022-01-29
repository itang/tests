#[cfg(test)]
mod tests {
    use crate::add3;

    #[test]
    fn test_add3() {
        assert_eq!(4, add3(1));
    }
}

pub fn add3(i: i64) -> i64 {
    i + 3
}
