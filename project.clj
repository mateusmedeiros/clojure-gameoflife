(defproject experimenting "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [cider/cider-nrepl  "0.8.2" ]
                 [clojure-lanterna  "0.9.4"  ]]
  :main ^:skip-aot experimenting.core
  :target-path "target/%s"
  :plugins [[lein-exec "0.3.4"]]
  :profiles {:uberjar {:aot :all}})
