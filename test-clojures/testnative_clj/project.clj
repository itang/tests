(defproject testnative_clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :plugins [[io.taylorwood/lein-native-image "0.3.0"]]
  :main ^:skip-aot testnative-clj.core
  :target-path "target/%s"
  :native-image {:name "my-app"                 ;; name of output image, optional
                 :graal-bin "/home/itang/dev-env/graalvm" ;; path to GraalVM home, optional
                 :opts ["--no-server" "--verbose" "--report-unsupported-elements-at-runtime"]}           ;; pass-thru args to GraalVM native-image, optional
  :profiles {:uberjar {:aot :all}})
