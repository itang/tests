(ns test-compojure.core
  (:require [compojure.core :refer :all]
            [ring.util.response :refer [redirect]]
            [org.httpkit.server :refer [run-server]]
            [selmer.parser :refer [render-file]]
            [hiccup.core :refer :all]
            [prone.middleware :as prone]
            [noir.response :as response]))

(defonce server (atom nil))

(defn stop-server! []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn- set-timeout! [millis f]
  (future (do
            (Thread/sleep millis)
            (f))))

(defn hello [req]
  (render-file "templates/hello.html" {:name "itang"}))

(defn exception [req]
  (throw (RuntimeException. "error")))

(defn handle-json [req]
  (response/json {:name "itang" :msg "Hello,world"}))

(defroutes myapp
  (GET "/" [] (html [:body
                     [:h1 "Hello World "]
                     [:ul
                      [:li [:a {:href "/hello" :target "_blank"} "Hello"]]
                      [:li [:a {:href "/exception" :target "_blank"} "Exception"]]
                      [:li [:a {:href "/stop" :target "_blank"} "Stop Server after 5 seconds"]]
                      [:li [:a {:href "/json" :target "_blank"} "JSON"]]]
                     [:script {:type "text/javascript"} "setInterval(function(){ window.location.reload(); }, 3000);"]]))
  (GET "/hello" [] hello)
  (GET "/exception" [] exception)
  (GET "/stop" [] (fn [req]
                    (set-timeout! 5000 stop-server!)
                    (redirect "/")))
  (GET "/json" [] handle-json))

(defn wrap-runtime-time [app]
  (fn [req]
    (let [start (System/currentTimeMillis)
          ret (app req)]
      (assoc-in ret [:headers "X-Runtime"] (- (System/currentTimeMillis) start)))))

(def app (-> myapp
             (prone/wrap-exceptions)
             (wrap-runtime-time)))

(def ^:const PORT 8080)

(defn -main []
  (reset! server (run-server app {:port PORT}))
  (println (str "start at :" PORT)))
