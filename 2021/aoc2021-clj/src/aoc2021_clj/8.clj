(ns aoc2021-clj.8
  (:require [clojure.string :as s]
            [clojure.set :as st]))

(defn make-input [filen]
  (as-> (slurp (str "resources/" filen)) f
        (s/split-lines f)
        (map #(s/split % #" \| ") f)
        (map (fn [d] (map #(s/split % #" ") d)) f)
        ))

(defn part1 [i]
  (count (filter some?
                 (map #{4 2 3 7}
                      (reduce concat (map #(->> %
                                                (second)
                                                (map count))
                                          i))))))

(def example
  [["acedgfb" "cdfbe" "gcdfa" "fbcad" "dab" "cefabd" "cdfgeb" "eafb" "cagedb" "ab"
    ] ["cdfeb" "fcadb" "cdfeb" "cdbaf"]])

(defn decode [i]
  (let [digits (first i)
        one (first (filter #(= 2 (count %)) digits))
        seven (first (filter #(= 3 (count %)) digits))
        eight (first (filter #(= 7 (count %)) digits))
        four (first (filter #(= 4 (count %)) digits))
        nine (first (filter #(and (= 6 (count %))
                                  (st/subset? (set four) (set %))) digits))
        zero (first (filter #(and (= 6 (count %))
                                  (st/subset? (set seven) (set %))
                                  (not (= (set nine) (set %)))) digits))
        six (first (filter #(and (= 6 (count %))
                                 (not (st/subset? (set seven) (set %)))) digits))
        three (first (filter #(and (= 5 (count %))
                                   (st/subset? (set seven) (set %))) digits))
        five (first (filter #(and (= 5 (count %))
                                  (st/subset? (set %) (set six))) digits))
        two (first (st/difference (set digits) #{zero one three four five six seven eight nine}))
        ds (mapv set [zero one two three four five six seven eight nine])]
    (reduce (fn [x y] (+ y (* 10 x))) 0 (map #(.indexOf ds (set %)) (second i)))))


(defn part2 [inp]
  (reduce + (map decode inp)))