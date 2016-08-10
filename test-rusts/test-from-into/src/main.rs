//use std::fmt::Debug;
use std::collections::LinkedList;

#[derive(Debug)]
struct SortedVec<T>(Vec<T>);

impl<'a, T: Ord + Clone> From<&'a [T]> for SortedVec<T> {
    fn from(slice: &[T]) -> Self {
        let mut vec = slice.to_owned();
        vec.sort();

        SortedVec(vec)
    }
}

impl<T: Ord + Clone> From<Vec<T>> for SortedVec<T> {
    fn from(mut vec: Vec<T>) -> Self {
        vec.sort();

        SortedVec(vec)
    }
}

impl<T: Ord + Clone> From<LinkedList<T>> for SortedVec<T> {
    fn from(list: LinkedList<T>) -> Self {
        let mut vec: Vec<T> = list.iter().cloned().collect();
        vec.sort();

        SortedVec(vec)
    }
}

fn main() {
    let vec = vec![1u8, 2, 3, 5, 4, 9, 6];

    let sorted = SortedVec::from(&vec[1..]);
    println!("sorted: {:?}", sorted);

    let sorted = SortedVec::from(vec);
    println!("sorted: {:?}", sorted);

    let mut linked_list: LinkedList<u8> = LinkedList::new();

    linked_list.extend(&[1, 2, 3]);
    let sorted = SortedVec::from(linked_list);
    println!("sorted: {:?}", sorted);
}
