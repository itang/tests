(ns test-compojure.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [selmer.parser :refer [render-file]]))

(defn hello [req]
  (render-file "templates/hello.html" {:name "itang"}))

(defroutes myapp
  (GET "/" [] "Hello World")
(GET "/hello" [] hello))

(defn -main []
  (run-server myapp {:port 5000}))
