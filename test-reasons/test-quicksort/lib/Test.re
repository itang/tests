let rec fib n =>
  switch n {
  | 0 => 0
  | 1 => 1
  | _ when n < 0 => 0
  | _ => fib (n - 1) + fib (n - 2)
  };

let rec quicksort list =>
  switch list {
  | [] => []
  | [x, ...tail] =>
    quicksort (List.filter (fun y => y < x) tail) @
    [x] @ quicksort (List.filter (fun y => y >= x) tail)
  };