(ns test-compojure.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [selmer.parser :refer [render-file]]
            [hiccup.core :refer :all]
            [prone.middleware :as prone]))

(defn hello [req]
  (render-file "templates/hello.html" {:name "itang"}))

(defn exception [req]
  (throw (RuntimeException. "error")))

(defroutes myapp
  (GET "/" [] (html [:body
                     [:h1 "Hello World "]
                     [:ul
                      [:li [:a {:href "/hello" :target "_blank"} "Hello"]]
                      [:li [:a {:href "/exception" :target "_blank"} "Exception"]]]]))
  (GET "/hello" [] hello)
  (GET "/exception" [] exception))

(def app (-> myapp
             prone/wrap-exceptions))

(defn -main []
  (run-server app {:port 5000})
  (println "start at :5000"))
