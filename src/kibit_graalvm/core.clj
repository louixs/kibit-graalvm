(ns kibit-graalvm.core
  (:require [cli-matic.core :refer [run-cmd]]
            [kibit.check :as check]
            [kibit.reporters :as reporters])
  (:gen-class))

(defn- flycheck-reporter [{:keys [alt column expr file line] :as result}]
  (let [location (str file ":" line ":" column)]
    (println location "warning: consider using" alt "instead.")))

(defn- check-file [{:keys [file]}]
  (let [revised (check/check-file file)
        warning (str "warning: " revised)]
    (println warning)
    (if (seq revised)
      2    ;; return if there's any revision warning
      0))) ;; if no revision, return success exit code

(defn- flycheck [{:keys [file]}]
  (let [revised (check/check-file file :reporter flycheck-reporter)]
    (if (seq revised)
      2    ;; return if there's any revision warning
      0)))

(def CONFIGURATION
  {:app {:command "kibit-graalvm"
         :description "GraalVM wrapped version of kibit for faste startup time."
         :version "0.1"}
   :commands [{:command "check" :short "c"
               :description ["Check whether expressions are idiomatic using kibit."
                             ""
                             "Prints a recommendation if not."]
               :opts [{:option "file"
                       :short "f"
                       :as "File path"
                       :type :string}]
               :runs check-file}
              {:command "flycheck" :short "f"
               :description ["returns error messages suitable for flycheck"
                             ""
                             "only prints recommendation and locations"]
               :opts [{:option "file"
                       :short 0
                       :as "File path"
                       :type :string}]
               :runs flycheck}]})

(defn -main
  "This is our entry point.
  Just pass parameters and configuration.
  Commands (functions) will be invoked as appropriate."
  [& args]
  (run-cmd args CONFIGURATION))


