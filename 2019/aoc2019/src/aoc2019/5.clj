(ns aoc2019.5
  (:require [aoc2019.machine :refer :all]))

(def raw-input (slurp "resources/5.txt"))

(def in (create-machine-spec raw-input))


(defn main1 []
  (:outputstream (run-machine-with-input in [1])))

(defn main2 []
  (:outputstream (run-machine-with-input in [5])))
