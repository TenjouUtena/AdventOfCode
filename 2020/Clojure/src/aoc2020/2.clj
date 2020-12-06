(ns aoc2020.2
  (:require [aoc2020.core :refer :all]))

(def i (read-file-as-strings "resources/Files/2/input.txt"))

(def rx #"([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)")

(defn test-pw1 [_ lt mt cc pw]
  (let [l (Integer/parseInt lt)
        m (Integer/parseInt mt)
        c (first cc)
        found (count (filter #(= c %) pw))]
    (and (<= l found) (>= m found))))

(defn test-pw2 [_ lt mt cc pw]
  (let [l (Integer/parseInt lt)
        m (Integer/parseInt mt)
        c (first cc)
        found (count (filter #(= c %) pw))]
    (and (or (= c (nth pw (dec l)))
             (= c (nth pw (dec m))))
         (not (and (= c (nth pw (dec l)))
                   (= c (nth pw (dec m))))))
    ))

(defn part1 []
  (let [pws (map #(re-matches rx %) i)]
    (count (filter #(apply test-pw1 %) pws))))

(defn part2 []
  (let [pws (map #(re-matches rx %) i)]
    (count (filter #(apply test-pw2 %) pws))))
