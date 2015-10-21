module Option where

type Option a = None | Some a

getOrElse : a -> Option a -> a
getOrElse e o =
  case o of
    None -> e
    Some a -> a

map : (a -> result) -> Option a -> Option result
map f o =
  case o of
    Some a -> Some (f a)
    None -> None

flatMap : (a -> Option b) -> Option a -> Option b
flatMap f o =
  case o of
    Some a -> f a
    None -> None
