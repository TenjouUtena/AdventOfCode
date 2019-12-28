(ns aoc2019.25
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]))


(def input (s/trim (slurp "resources/25.txt")))



(defn main1 []
  (loop [machine (run-machine (create-machine-spec input))]
    (println (reduce str (map char (:outputstream machine))))

    (recur (run-machine-base  (-> machine
                                  (assoc :outputstream [])
                                  (assoc :inputwait false)
                                  (assoc :inputstream (concat (map int (read-line)) [10]))
                                  )))))

