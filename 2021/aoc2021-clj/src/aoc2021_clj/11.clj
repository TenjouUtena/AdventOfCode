(ns aoc2021-clj.11
  (:require [clojure.string :as s]))

(def testinp
  "5483143223274585471152645561736141336146635738547841675246452176841721688288113448468485545283751526")

(def inp
  "3172537688456648312563745126538321148885434274775813621885827582213132688787526876351127877242787273")

(defn make-input [inp]
  (map #(- (int %) 48) inp))

(defn neighbors [i]
  (filterv #(and (<= 0 %) (> 100 %))
           (mapv #(+ i %) (concat
                   (if (> 9 (mod i 10)) [-9 1 11] [])
                   (if (< 0 (mod i 10)) [-11 -1 9] [])
                   [-10 10]))))

(defn prop-one [[i _]]
  (loop [state (mapv inc i)
         open (set (filter #(< 9 (nth state % -1)) (range (count i))))
         closed #{}]
    (if (empty? open)
      [(mapv #(if (< 9 %) 0 %) state) (count (filter #(< 9 %) state))]
      ;state
      (let [c (first open)
            nstate (reduce (fn [m k] (update m k inc)) state
                                    (neighbors c))]
        ;(println (partition 10 state) open closed c)
        (recur nstate
               (clojure.set/difference
                 (set (filter #(< 9 (nth nstate % -1)) (range (count i))))
                 (conj closed c))
               (conj closed c))
        ))))

(defn part1 [inp]
  (second (reduce (fn [[state count] _]
                    (let [res (prop-one [state 0])]
                      [(first res) (+ count (second res))]
                      ))
                  [inp 0]
                  (range 100))))


(defn part2 [inp]
  (count (take-while #(> 100 (second %)) (iterate prop-one [inp 0]))))

