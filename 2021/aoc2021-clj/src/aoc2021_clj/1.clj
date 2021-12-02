(ns aoc2021-clj.1
  (:require [clojure.string
             :as
             s]))

(def lon (map bigint (s/split-lines (slurp "resources/ic.txt"))))

(defn weirdcount [v]
  (count (filter #(apply < %) (map vector v (rest v)))))

(defn part1 []
  (weirdcount lon))

(defn part2 []
  (let [nl (map #(reduce + %) (map vector lon (rest lon) (rest (rest lon))))]
    (weirdcount nl)))