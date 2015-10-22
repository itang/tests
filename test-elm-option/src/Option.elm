module Option
    ( Option(..)
    , getOr
    , map, flatMap
    ) where

type Option a = None | Some a

{-| Get a value from the Option, return the defalut value when it's None.

    getOr 100 (Some 4) == 4

    getOr 100 None == 100
-}
getOr : a -> Option a -> a
getOr default opt =
  case opt of
    None -> default
    Some value -> value

{-| Apply a function to the value of a Option.

    map sqrt (Some 4) == Some 2

    map sqrt None == None
-}
map : (a -> result) -> Option a -> Option result
map f opt =
  case opt of
    Some value -> Some (f value)
    None -> None

{-| Apply a function which return A Option to the value of a Option.

    flatMap (Some << sqrt) (Some 4) == Some 2

    flatMap (Some << sqrt) None == None
-}
flatMap : (a -> Option b) -> Option a -> Option b
flatMap f opt =
  case opt of
    Some value -> f value
    None -> None
