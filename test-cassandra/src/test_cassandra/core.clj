(ns test-cassandra.core
  (:gen-class)
  (:require [clojurewerkz.cassaforte.client :as cc]
            [clojurewerkz.cassaforte.cql :as cql]
            [clojurewerkz.cassaforte.query :refer :all]))

(def ^:cosnt HOST "127.0.0.1")

(def ^:const USER_TABLE_NAME "users")

(def ^:const USER_POSTS_TABLE_NAME "user_posts")

(def ^:const TEST_KEYSPACE "test_keyspace")

(defmacro try! [& body]
  `(try [true (do ~@body)]
        (catch Exception ~'e
          (-> ~'e .getMessage println)
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
  (try! (cql/drop-table conn USER_TABLE_NAME))
  (try! (cql/drop-table conn USER_POSTS_TABLE_NAME))

  (try! (cql/create-table conn USER_TABLE_NAME
                          (column-definitions {:name        :varchar
                                               :age         :int
                                               :city        :varchar
                                               :primary-key [:name]})))
  (try! (cql/create-table conn USER_POSTS_TABLE_NAME
                          (column-definitions {:username    :varchar
                                               :post_id     :varchar
                                               :body        :text
                                               :primary-key [:username :post_id]})))
  #_(try! (cql/delete conn USER_POSTS_TABLE_NAME))
  #_(try! (cql/delete conn USER_POSTS_TABLE_NAME)))

(defn init-users-data [conn]
  (doto conn
    (cql/insert USER_TABLE_NAME {:name "Alex" :city "Munich" :age (int 19)})
    (cql/insert USER_TABLE_NAME {:name "Robert" :city "Berlin" :age (int 25)})
    (cql/insert USER_TABLE_NAME {:name "Sam" :city "San Francisco" :age (int 21)})

    (cql/insert USER_POSTS_TABLE_NAME {:username "Alex" :post_id "post1" :body "first post body"})
    (cql/insert USER_POSTS_TABLE_NAME {:username "Alex" :post_id "post2" :body "second post body"})
    (cql/insert USER_POSTS_TABLE_NAME {:username "Alex" :post_id "post3" :body "third post body"})))

(defn print-data [conn]
  (doseq [user (cql/select conn USER_TABLE_NAME (where :name [:in ["Alex" "Robert"]]))]
    (println user))
  (doseq [post (cql/select conn USER_POSTS_TABLE_NAME
                           (columns :post_id)
                           (where :username "Alex"
                                  :post_id [> "post1"]
                                  :post_id [< "post3"]))]
    (println post)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [conn (cc/connect [HOST])]
    (init-keyspace conn)
    (use-keyspace conn)
    (time (create-tables conn))
    (init-users-data conn)
    (print-data conn)))