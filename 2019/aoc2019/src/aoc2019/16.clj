(ns aoc2019.16
  (:require [clojure.string :as s]))

(defn nthiter [n]
  (rest (cycle (concat (repeat (inc n) 0) (repeat (inc n) 1) (repeat (inc n) 0) (repeat (inc n) -1)))))

(defn fft-digit [nn d]
  (Math/abs (rem (reduce + (map * nn (nthiter d))) 10)))

(defn fft [nn]
  (let [n (map #(Integer/parseInt (str %)) nn)]
    (for [x (range (count n))]
      (fft-digit n x))))

(def test1 "80871224585914546619083218645595")
(def inp (s/trim (slurp "resources/16.txt")))

(defn main1 []
  (nth (iterate fft inp) 100))

(defn main2 []
  (let [ii (nth (iterate fft (apply str (repeat 1000 inp))) 100)
        dd (bigint (apply str (take 7 inp)))
        nn (take 8 (drop dd ii))]
    nn))
