(ns aoc2017)
(require '[clojure.string :as str])

(defn makesheet []  (map #(str/split % #" ") (str/split (slurp "3.txt") #"\r\n")))


(defn part1 []
  (count (filter #(apply distinct? %) (makesheet))))

(defn part2 []
  (count (filter #(apply distinct? (map sort %)) (makesheet))))

(conj []  (part1) (part2))




