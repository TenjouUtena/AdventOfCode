(ns aoc2020.5
  (:require [aoc2020.core :refer :all]
            [clojure.string :as s]))

(def i (read-file-as-strings "resources/Files/5/input.txt"))

(defn seat-value [seat-code]
  (Integer/parseInt (-> seat-code
                        (s/replace "B" "1")
                        (s/replace "F" "0")
                        (s/replace "L" "0")
                        (s/replace "R" "1")
                        ) 2))

(def seats (map seat-value i))

(defn part1 []
  (first (sort > seats)))

(defn least []
  (first (sort < seats)))

(defn part2 []
  (clojure.set/difference (set (range (least) (inc (part1)))) (set seats)))