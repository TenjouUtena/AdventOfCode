(ns aoc2020.6
  (:require [aoc2020.core :refer :all]
            [clojure.string :as s]))


(def i (map (comp set #(s/replace % "\n" ""))
            (s/split (slurp "resources/Files/6/input.txt") #"\n\n")))

(defn part1 [] (reduce + (map count i)))

(def i2 (map (fn [x] (reduce clojure.set/intersection (map set x)))
             (map s/split-lines (s/split (slurp "resources/Files/6/input.txt") #"\n\n"))))

(defn part2 [] (reduce + (map count i2)))


