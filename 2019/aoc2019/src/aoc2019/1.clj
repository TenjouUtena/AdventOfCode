(ns aoc2019.1
  (:require [clojure.string :as str]))


(defn loadff [] (map #(Integer/parseInt %) (str/split-lines (slurp "resources/1.txt"))))


(defn fuelcost [weight]
  (- (int (Math/floor (/ weight 3))) 2))

(defn fullfuelcost [w]
  (loop [we 0
         f (fuelcost w)]
    (if (<= f 0)
      we
      (recur (+ we f) (fuelcost f)))))

(defn main1 []
  (reduce + (map fuelcost (loadff))))


(defn main2 []
  (reduce + (map fullfuelcost (loadff))))