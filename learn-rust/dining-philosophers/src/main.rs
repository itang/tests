use std::thread;
use std::sync::{Mutex, Arc};

struct Philosopher {
    name: String,
    left: usize,
    right: usize,
}

impl Philosopher {
    fn new(name: &str, left: usize, right: usize) -> Self {
        Philosopher {
            name: name.to_string(),
            left: left,
            right: right,
        }
    }

    fn eat(&self, table: &Table) {
        let _left = table.forks[self.left].lock().unwrap();
        let _right = table.forks[self.right].lock().unwrap();

        println!("{} is eating.", self.name);

        thread::sleep_ms(1000);

        println!("{} is done eating.", self.name);
    }
}

struct Table {
    forks: Vec<Mutex<()>>,
}


fn main() {
    let table = Arc::new(Table {
        forks: vec![
         Mutex::new(()),
         Mutex::new(()),
         Mutex::new(()),
         Mutex::new(()),
         Mutex::new(()),
        ],
    });

    let philosophers: Vec<Philosopher> = vec![
      Philosopher::new("[1]Judith Butler", 0, 1),
      Philosopher::new("[2]Gilles Deleuze", 1, 2),
      Philosopher::new("[3]Karl Marx",2, 3),
      Philosopher::new("[4]Emma Goldman",3, 4),
      Philosopher::new("[5]Michel Foucault", 0, 4),
    ];

    // for p in &philosophers {
    //     // getting a reference to each philosopher in turn.
    //     p.eat();
    // }

    let handles: Vec<_> = philosophers.into_iter()
                                      .map(|p /* : Philosopher */| {
                                          let table: Arc<Table> = table.clone();
                                          thread::spawn(move || {
                                              p.eat(&table); // &Arc<T> => &T
                                          })
                                      })
                                      .collect();

    for h in handles {
        h.join().unwrap();
    }
}
