(ns vanga.core
  (:require
   [vanga.cmpr :as cmpr])
  (:gen-class))

(defn -main
  [& args]
  (let [result (cmpr/cmpr "265:265" "13:13") ]
    (prn "Result:" result)) )
