module Main where

import Option exposing (Option(Some, None), map, flatMap, getOr)
import String exposing (toUpper)
import Html exposing (..)
import Debug


-- MAIN
main : Html
main =
  let
    _ = Debug.log "init model" model
  in
    view model


-- MODEL
type alias Model = List (Option String)


model : Model
model = init

init : List (Option String)
init =
  [Some "Java", Some "Julia", Some "Clojure", Some "Scala", Some "Frege", Some "Ruby", Some "Rust", Some "Elixir", None]

-- VIEW
view : Model -> Html
view model =
  ul [] (List.map item model)


wrap : Option String -> String
wrap =
  (Option.map toUpper) >> getOr "..."


item : Option String -> Html
item a =
  li [] [ text << wrap <| a ]
