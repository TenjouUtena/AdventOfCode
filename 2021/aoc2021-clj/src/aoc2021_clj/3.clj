(ns aoc2021-clj.3
  (:require [clojure.string :as s]))

(def inp (s/split-lines (slurp "resources/3.txt")))

(defn part1' [i cmpfunc]
  (->> i
       (apply map vector)
       (map #(map (fn [x] (Character/digit x 10)) %))
       (map #(apply + %))
       (map #(if (cmpfunc % (/ (count i) 2)) 0 1))
       )
  )

(defn part1 [i]
  [(part1' inp <) (part1' inp >=) ])

(defn part2' [i cmpfunc]
  (loop [lov i
         digit 0]
    (if (= 1 (count lov))
      (first lov)
      (let [t (nth (part1' lov cmpfunc) digit)]
        (recur (filter (fn [x]
                         (= t (Character/digit (nth x digit) 10))) lov)
               (inc digit)))
      )))

(defn part2 [i]
  (* (Integer/parseInt (part2' inp <) 2) (Integer/parseInt (part2' inp >=) 2)))