

(ns aoc2017)
(require '[clojure.string :as str])
(require '[clojure.math.combinatorics :as combo])

(defn makesheet []  (map (fn [x] (map #(Integer. %) x))  (map #(str/split % #"\t") (str/split (slurp "resources/2.txt") #"\n"))))

(defn checksum [ll]
    (- (apply max ll) (apply min ll)))

(defn checkdivide [x y]
  (if (= 0 (mod x y))
     (/ x y)
     0))

(defn checksum2 [ll]
  (reduce + (map #(apply checkdivide %) (combo/combinations (sort > ll) 2))))

(defn run1 [] (reduce + (map checksum (makesheet))))
(defn run2 [] (reduce + (map checksum2 (makesheet))))






