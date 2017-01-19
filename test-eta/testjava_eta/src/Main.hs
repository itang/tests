module Main where

import Java

foreign import java unsafe "@static eta.first.Utils.createFile"
  createFile :: String -> IO ()

main :: IO ()
main = createFile "hello.txt"
