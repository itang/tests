(ns test-compojure.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [selmer.parser :refer [render-file]]
            [hiccup.core :refer :all]))

(defn hello [req]
  (render-file "templates/hello.html" {:name "itang"}))

(defroutes myapp
  (GET "/" [] (html [:body [:h1 "Hello World "] [:a {:href "/hello" :target "_blank"} "Hello"]]))
  (GET "/hello" [] hello))

(defn -main []
  (run-server myapp {:port 5000}))
