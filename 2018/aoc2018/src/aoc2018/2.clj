(ns aoc2018.2
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn get-stuff []
  (str/split (slurp "resources/2.txt") #"\n"))

(defn- get-count
  [codes num]
  (reduce + (map (fn [x] (if (some #(= (second %) num) (frequencies x)) 1 0 )) codes)))

(defn day2-1 []
  (let [codes (get-stuff)
        two (get-count codes 2)
        three (get-count codes 3)]
    (* two three)))

(defn hamm [str1 str2]
  (count (filter true? (map #(not (= (first %) (second %))) (map vector str1 str2)))))

(defn day2-2 []
  (let [codes (get-stuff)
        cc (combo/combinations codes 2)]
    (doseq [code cc]
      (if (= 1 (apply hamm code))
        (print code)))))
