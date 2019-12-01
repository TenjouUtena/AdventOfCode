(ns aoc2019.1
  (:require [clojure.string :as str]
            [aoc2019.core :refer :all]))


(defn fuelcost [weight]
  (- (int (Math/floor (/ weight 3))) 2))

(defn fullfuelcost [w]
  (loop [we 0
         f (fuelcost w)]
    (if (<= f 0)
      we
      (recur (+ we f) (fuelcost f)))))

(def fff "resources/1.txt")

(def input (read-file-as-ints fff))

(defn main1 []
  (reduce + (map fuelcost input)))


(defn main2 []
  (reduce + (map fullfuelcost input)))