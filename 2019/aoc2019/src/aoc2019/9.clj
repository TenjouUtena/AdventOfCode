(ns aoc2019.9
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]))

(def input "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99")
(def input2 "1102,34915192,34915192,7,4,7,99,0")
(def input-r (s/trim (slurp "resources/9.txt")))

(def machine (create-machine-spec input-r))

(defn main1 []
  (run-machine-with-input machine [1]))

(defn main2 []
  (run-machine-with-input machine [2]))
