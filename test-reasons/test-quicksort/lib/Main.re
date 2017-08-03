let run () => {
  print_endline "Hello world";
  let list = Test.quicksort [23, 43, 213, 1, 32, 32, 2, 2, 4, 9, 10];
  list |> List.map string_of_int |> String.concat " " |> print_endline
  /* print_string (String.concat " " (List.map string_of_int list)) */
};

let add2 x => x + 2;