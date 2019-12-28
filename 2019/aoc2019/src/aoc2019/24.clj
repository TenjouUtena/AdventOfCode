(ns aoc2019.24
  (:require [aoc2019.grid :as g]
            [clojure.string :as s]))


(def input (s/split (slurp "resources/24.txt") #"\n"))

(defn passable? [grid loc]
  (= (:tile (g/get-node grid loc)) \#))



(defn makegrid []
  (reduce-kv (fn [m k v]
               (g/set-node m v k))
             (assoc g/base-grid :passable? passable?)
             (into {} (apply concat
                             (map-indexed (fn [y row]
                                            (map-indexed
                                             (fn [x v]
                                               [[x y] {:tile v}])
                                             row))
                                          input)))))


(defn simulate-one-step [grid]
  (reduce (fn [grid node]
            (g/set-node grid (second node) (first node)))
            grid
            (for [x (range 5)
                  y (range 5)
                  :let [n (g/get-node grid [x y])
                        bugs (count (g/passable-adjacent-locs grid [x y]))]]
              (cond
                (and (= (:tile n) \#) (= 1 bugs))
                [[x y] {:tile \#}]
                (and (= (:tile n) \.) (or (= 1 bugs) (= 2 bugs)))
                [[x y] {:tile \#}]
                :else
                [[x y] {:tile \.}]))))

(defn calc-num [grid]
  (reduce + (map-indexed (fn [ind val]
                           (if (= val \#)
                             (Math/pow 2 ind)
                             0))
                         (apply concat (g/printable-grid grid)))))



(defn find-repeat-state [grid]
  (loop [obs []
         gr grid]
    (let [bd (calc-num gr)]
      (if (some #(= % bd) obs)
        bd
        (recur (conj obs bd) (simulate-one-step gr))))))

