(ns aoc2021-clj.9
  (:require [clojure.string :as s]))

(defn make-input [filen]
  (mapv (fn [x] (mapv #(- (int %) 48) x)) (s/split-lines (slurp (str "resources/" filen)))))

(defn get-at [grid [x y]]
  (get-in grid [y x] 9))

(defn neighbors [[x y]]
  (map #(map + [x y] %) [[0 1] [1 0] [-1 0] [0 -1]]))

(defn lowest? [grid [x y]]
  (->> (neighbors [x y])
       (map #(get-at grid %))
       (map #(< (get-at grid [x y]) %))
       (reduce (fn [z w] (and z w)))
       ))

(defn basin-size [grid [x y]]
  (loop [open #{[x y]}
         closed #{}
         ]
    (if (empty? open)
      (count closed)
      (let [candidate (first open)
            new-open (clojure.set/difference
                       (set (filter #(> 9 (get-at grid %)) (neighbors candidate)))
                       closed)]
        (recur (clojure.set/difference
                 (clojure.set/union open new-open)
                 #{candidate})
               (conj closed candidate))
        ))))

(defn lowest-points [grid]
  (for [x (range 100)
        y (range 100)
        :when (lowest? grid [x y])]
    [x y]))

(defn part1 [grid]
  (reduce + (map #(inc (get-at grid %))
                (lowest-points grid))))

(defn part2 [grid]
  (apply * (take 3 (reverse (sort (map #(basin-size grid %) (lowest-points grid)))))))