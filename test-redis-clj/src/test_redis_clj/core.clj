(ns test-redis-clj.core
  (:require
    [org.httpkit.server :refer [run-server]] ; Web server
    [taoensso.carmine :as redis]) ; Redis client
  (:import
    clojure.lang.Murmur3 ; Look what I found!
    org.apache.commons.validator.routines.UrlValidator))

(def validator (UrlValidator. (into-array ["http" "https"])))
(def hash-url (comp (partial format "%x")
                    #(Murmur3/hashUnencodedChars %)))

(defn- create-short-url [path]
  (let [rand-str (hash-url path)]
    (redis/wcar nil
      (redis/set rand-str path))
    (str "http://localhost:8080/" rand-str)))

(defn- uri-as-path [uri] 
  (apply str (rest uri))) ; Drop '/'

(defn handle-create [{path :uri :as request}]
  (let [path (uri-as-path path)]
  (if (.isValid validator path) ; 
    {:status 200 :body (create-short-url path)}
    {:status 401 :body "Invalid Url provided"})))

(defn handle-redirect [{path :uri :as request}]
  (let [path (uri-as-path path)
        url (redis/wcar nil (redis/get path))]
    (if url
      {:status 302 :body "" :headers {"Location" url}}
      {:status 404 :body "Unknown destination."})))

(defn handler [{method :request-method :as req}]
  (case method
    :get (handle-redirect req)
    :post (handle-create req)))

(defn -main [& args]
  (println "start...")
  (run-server handler {:port 8080}))
