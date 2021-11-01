(ns build
  "clj-new's build script.

  clojure -T:build ci

  For more information, run:

  clojure -A:deps -T:build help/doc"
  (:require [clojure.tools.build.api :as b]
            [org.corfield.build :as bb]))

(def lib 'com.github.seancorfield/clj-new)
(def version (format "1.2.%s" (b/git-count-revs nil)))

(defn ci "Run the CI pipeline of tests (and build the JAR)." [opts]
  (-> opts
      (assoc :lib lib :version version)
      (bb/run-tests) ; there are no tests yet, but that's OK
      (bb/clean)
      (assoc :src-pom "template/pom.xml")
      (bb/jar)))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (-> opts
      (assoc :lib lib :version version)
      (bb/deploy)))
