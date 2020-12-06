(ns aoc2020.3
  (:require [aoc2020.core :refer :all]))

(def i (read-file-as-strings "resources/Files/3/input.txt"))


(defn count-slope [down right]
  (count (filter #(= \# %)
                 (map #(nth (cycle %1) (dec %2))
                      (take-nth down i)
                      (iterate #(+ right %) 1)))))

(defn part1 []
  (count-slope 1 3))

(defn part2 []
  (*
    (count-slope 1 1)
    (count-slope 1 3)
    (count-slope 1 5)
    (count-slope 1 7)
    (count-slope 2 1)))

