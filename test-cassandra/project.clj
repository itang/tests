(defproject test-cassandra "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojurewerkz/cassaforte "2.0.0-beta6"]]
  :main ^:skip-aot test-cassandra.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
