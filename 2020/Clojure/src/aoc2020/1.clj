(ns aoc2020.1
  (:require [aoc2020.core :refer :all]))

(def i (read-file-as-ints "resources/Files/1/input.txt"))

(defn part1 []
  (for [x i
        y i
        :when (= (+ x y) 2020)]
    (* x y)))

(defn part2 []
  (for [x i
        y i
        z i
        :when (= (+ x y z) 2020)]
    (* x y z)))

