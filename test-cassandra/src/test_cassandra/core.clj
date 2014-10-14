(ns test-cassandra.core
  (:gen-class)
  (:require [clojurewerkz.cassaforte.client :as cc]
            [clojurewerkz.cassaforte.cql :as cql]
            [clojurewerkz.cassaforte.query :refer :all]))

(def ^:cosnt HOST "127.0.0.1")

(def ^:const UN "users")

(def ^:const UPN "user_posts")

(def ^:const TEST_KEYSPACE "test_keyspace")

(defmacro try! [& body]
  `(try [true (do ~@body)]
     (catch Exception ~'e
       #_(-> ~'e .getMessage println)
       [false ~'e])))

(defn- init-keyspace [conn]
  (try!
    (cql/create-keyspace conn
      TEST_KEYSPACE
      (with {:replication
              {:class              "SimpleStrategy"
               :replication_factor 1}}))))
(defn- use-keyspace [conn]
  (cql/use-keyspace conn TEST_KEYSPACE))

(defn- create-tables [conn]
  (try! (cql/drop-table conn UN))
  (try! (cql/drop-table conn UPN))

  (try! (cql/create-table conn UN
          (column-definitions {:name        :varchar
                               :age         :int
                               :city        :varchar
                               :primary-key [:name]})))
  (try! (cql/create-table conn UPN
          (column-definitions {:username    :varchar
                               :post_id     :varchar
                               :body        :text
                               :primary-key [:username :post_id]})))
  #_(try! (cql/delete conn USER_POSTS_TABLE_NAME))
  #_(try! (cql/delete conn USER_POSTS_TABLE_NAME)))

(defn init-users-data [conn]
  (doto conn
    (cql/insert UN {:name "Alex" :city "Munich" :age (int 19)})
    (cql/insert UN {:name "Robert" :city "Berlin" :age (int 25)})
    (cql/insert UN {:name "Sam" :city "San Francisco" :age (int 21)})

    (cql/insert UPN {:username "Alex" :post_id "post1" :body "first post body"})
    (cql/insert UPN {:username "Alex" :post_id "post2" :body "second post body"})
    (cql/insert UPN {:username "Alex" :post_id "post3" :body "third post body"})))

(defn print-data [conn]
  (doseq [user (cql/select conn UN (where :name [:in ["Alex" "Robert"]]))]
    (println user))
  (doseq [post (cql/select conn UPN (columns :post_id) (where :username "Alex" :post_id [> "post1"] :post_id [< "post3"]))]
    (println post)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (try!
    (let [conn (cc/connect [HOST])]
      (init-keyspace conn)
      (use-keyspace conn)
      (time (create-tables conn))
      (init-users-data conn)
      (print-data conn))))