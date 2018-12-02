(ns aoc2018.1
  (:require [clojure.string :as stri]))

(defn- read-nums
  []
  (map read-string (stri/split (slurp "resources/1.txt") #"\n")))

(defn day1-1 []
  (reduce + (read-nums)))

(defn day1-2 []
  (loop [
         val 0
         vals [0]
         to-process (cycle (read-nums))
         ]
    (let [nval (+ val (first to-process))]
      (if (some #(= nval %) vals)
        nval
        (recur nval (conj vals nval) (drop 1 to-process))))))
