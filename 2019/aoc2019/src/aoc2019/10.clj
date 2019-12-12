(ns aoc2019.10
  (:require [clojure.string :as s]
            [clojure.math.combinatorics :as combo]))


(def input (s/split-lines (slurp "resources/10.txt")))

(defn detect-meteors [input]
  (into {} (for [x (range (count (first input)))
                 y (range (count input))
                 :when (= (nth (nth input y) x) \#)]
             [[x y] {:seen [] :unseen []}]
             )))


(defn sees? [[x1 y1] [x2 y2] galaxy]
  (not (some identity (if (= 0 (- x1 x2))
                        ;; Do something for infinite slope
                        (for [y (if (< y1 y2)
                                  (range (inc y1) y2)
                                  (range (dec y1) y2 -1))]
                          (some #(= [x1 y] %) (keys galaxy)))
                        (let [slope (/ (- y2 y1)
                                       (- x2 x1))
                              b (- y1 (* slope x1))]
                          (for [x (if (< x1 x2)
                                     (range (inc x1) x2)
                                     (range (dec x1) x2 -1))
                                :let [y (+ (* slope x) b)]
                                :when (integer? y)]
                            (some #(= [x y] %) (keys galaxy))))))))


(defn map-visibility [galaxy]
  (let [pairs (combo/combinations (keys galaxy) 2)]
    (loop [tt (first pairs)
           rr (rest pairs)
           tg galaxy]
      (cond
        (nil? tt)
        tg
        (some #(= (nth tt 1) %) (concat (get-in [(first tt) :seen] tg) (get-in [(first tt) :unseen] tg)))
        (recur (first rr) (rest rr) tg)
        :else
        (recur (first rr) (rest rr) (-> tg
                                     (update-in [(first tt) (if (sees? (first tt) (nth tt 1) tg) :seen :unseen)] conj (nth tt 1))
                                     (update-in [(nth tt 1) (if (sees? (first tt) (nth tt 1) tg) :seen :unseen)] conj (first tt))))
        ))))






                                        ;(def vis (map-visibility (detect-meteors input)))
                                        ;(def best (first (sort #(> (count (:seen (val %1))) (count (:seen (val %2)))) vis ))
;(count (:seen (val best)))



(defn angle [[x1 y1] [x2 y2]]
  (let [raw-angle (- (Math/atan2 (- y1 y2) (- x1 x2)) (* 0.5 Math/PI))]
    (println x1 y1 x2 y2 raw-angle)
    (if (>= raw-angle 0)
      raw-angle
      (+ raw-angle (* 2 Math/PI)))
   ))

(defn determine-order [ll from]
  (sort #(< (angle from %1) (angle from %2)) ll))
