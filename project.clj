(defproject kibit-graalvm "0.1.0"
  :description "Kibit with GraalVM"
  :url "https://github.com/louixs/kibit-graalvm"
  :dependencies [[cli-matic "0.3.8"]
                 [jonase/kibit "0.1.7"]
                 [org.clojure/clojure "1.9.0"]]
  :main kibit-graalvm.core
  :aot :all)
