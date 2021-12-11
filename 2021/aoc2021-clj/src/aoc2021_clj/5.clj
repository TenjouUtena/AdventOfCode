(ns aoc2021-clj.5
  (:require [clojure.string :as s]))

(def inp (s/split-lines (slurp "resources/5.txt")))


(defn uall [m x1 x2 y1 y2]
  (let [dx (- x2 x1)
        dy (- y2 y1)
        run (if (zero? dx) 0 (/ dx (Math/abs dx)))
        rise (if (zero? dy) 0 (/ dy (Math/abs dy)))
        num (inc (max (Math/abs dx) (Math/abs dy)))
        ]
    (reduce (fn [m z] (update m [(+ x1 (* run z)) (+ y1 (* rise z))] (fnil inc 0)))
            m
            (range 0 num (/ num (Math/abs ^int num))))))

(defn loop1 [input]
  (loop [i input
         m {}]
    (if
      (empty? i)
      m
      (let [[_ x1s y1s x2s y2s] (re-find #"(\d+),(\d+) -> (\d+),(\d+)" (first i))
            x1 (Integer/parseInt x1s)
            x2 (Integer/parseInt x2s)
            y1 (Integer/parseInt y1s)
            y2 (Integer/parseInt y2s)]
        (if (or (= x1 x2) (= y1 y2))
          (recur (rest i) (uall m x1 x2 y1 y2))
          (recur (rest i) (uall m x1 x2 y1 y2)))
        )
      )))

(defn part1 [input]
  (count (filter #(< 1 (second %)) (loop1 input))))

