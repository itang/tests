module Counter where

import Html exposing (..)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)
import StartApp.Simple

import Http
import Json.Decode as Json exposing ((:=))
import Task exposing (..)

-- MAIN
main : Signal Html
main = StartApp.Simple.start { model = init , update = update, view = view }


-- MODEL
type alias DayCounter = { day : String , counter : Int }

type alias Model = List DayCounter


init : Model
init = [{ day = "2015-10-17", counter = 1}] -- TODO


-- UPDATE
type Action = Show DayCounter

update : Action -> Model -> Model
update action model =
  case action of
    _ -> model


-- VIEW
view : Signal.Address Action -> Model -> Html
view address model =
  ul [ class "days" ] (List.map (dayItem address) model)

dayItem : Signal.Address Action -> DayCounter -> Html
dayItem address dayCounter =
  li [] [ text (dayCounter.day ++ ": " ++ (toString dayCounter.counter)) ]
