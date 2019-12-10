(ns aoc2019.8
  (:require [aoc2019.core :refer :all]
            [aoc2019.machine :refer :all]
            [clojure.string :as s]))



(def input (s/trim (slurp "resources/8.txt")))


(def split (partition (* 25 6) input))

(def thing (count split))

(def ss (map frequencies split))

(def gg (sort #(< (get %1 \0) (get %2 \0)) ss ))

(defn main2 []
  (map #(s/replace (apply str %) #"0" " ") (partition 25 (for [x (range (* 25 6))]
                           (some #(when (< (Integer/parseInt (str %)) 2) %) (map #(nth % x) split))))))
