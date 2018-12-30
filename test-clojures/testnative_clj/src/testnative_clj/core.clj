(ns testnative-clj.core
  (:require [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& path]
  (println "Hello, World!")
  (-> (read *in*)
      (get-in (mapv read-string path))
      pprint))
