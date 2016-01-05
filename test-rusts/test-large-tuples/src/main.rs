use std::fmt;

fn main() {
    let ts = (1,
              2,
              3,
              4,
              5,
              6,
              7,
              8,
              9,
              10,
              11,
              12,
              13,
              14,
              15,
              16,
              17,
              18,
              19,
              20,
              21,
              22,
              23,
              24,
              25,
              26,
              27,
              28,
              29,
              30,
              31,
              32,
              33,
              34,
              35,
              36,
              37,
              38,
              39,
              40,
              41,
              42,
              43,
              44,
              45,
              46,
              47,
              48,
              49,
              50,
              51,
              52,
              53,
              54,
              55,
              56,
              57,
              58,
              59,
              60,
              61,
              62,
              63,
              64,
              65,
              66,
              67,
              68,
              69,
              70,
              71,
              72,
              73,
              74,
              75,
              76,
              77,
              78,
              79,
              80,
              81,
              82,
              83,
              84,
              85,
              86,
              87,
              88,
              89,
              90,
              91,
              92,
              93,
              94,
              95,
              96,
              97,
              98,
              99,
              100);


    // Rust的Tuple元素个数没有限制, 不像Scala最大支持22个而已.
    println!("{:?}", ts.99);

    fn test_printable() {
        // println!("{:?}", ts); // the trait `core::fmt::Debug` is not implemented for the type
        let tuple_of_tuples = ((1u8, 2u16, 2u32), (4u64, -1i8), -2i16);

        // Tuples are printable
        println!("tuple of tuples: {:?}", tuple_of_tuples);
    }
    test_printable();

    fn copy_or_move() {
        let a = (1, 2, 3);
        println!("{:?}", a);
        // copy or move
        // copy
        fn swap(a: (i32, i32, i32)) -> (i32, i32, i32) {
            let (a1, a2, a3) = a;
            (a3, a2, a1)
        }
        println!("{:?}", swap(a));
        println!("{:?}", a);
        assert_eq!(a, (1, 2, 3));
        assert_eq!(swap(a), (3, 2, 1));
    }
    copy_or_move();


    fn mut_borrowing() {
        fn change_swap(a: &mut (i32, i32)) {
            let t = a.1;
            (*a).1 = a.0;
            (*a).0 = t;
        }

        let mut p = (1, 2);
        change_swap(&mut p);
        assert_eq!(p, (2, 1));
        println!("p: {:?}", p);
    }
    mut_borrowing();

    fn test_new_types() {
        #[derive(Debug)]
        struct Matrix(f32, f32, f32, f32);

        impl fmt::Display for Matrix {
            fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
                write!(f, "Matrix[{}, {}, {}, {}]", self.0, self.1, self.2, self.3)
            }
        }
        let m = Matrix(1.1, 1.2, 2.1, 2.2);
        println!("{:?}", m);
        assert_eq!(m.0, 1.1);

        let Matrix(m1, _, _, m4) = m;
        assert_eq!(m1, 1.1);
        assert_eq!(m4, 2.2);

        println!("{}", m);
    }
    test_new_types();
}
