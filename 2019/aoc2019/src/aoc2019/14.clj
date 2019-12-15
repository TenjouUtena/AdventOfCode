(ns aoc2019.14
  (:require [clojure.string :as s]))

(def input (s/trim (slurp "resources/14.txt")))

(defn extract-reaction [react]
  (let [mm (re-matcher #"(\d+) ([A-Z]+)" react)]
    (loop [retval []
           m1 (re-find mm)]
      (if m1
        (recur (conj retval [(bigint (nth (re-groups mm) 1)) (nth (re-groups mm) 2)])
               (re-find mm))
        {(last (last retval)) {:num (first (last retval))
                               :ingredients (butlast retval)}}))))

(def reactions (into {} (map extract-reaction (s/split-lines input))))

(defn synth-material [qty fuel]
  (loop [reagent fuel
         reagent-qty qty
         backlog []
         leftover {}
         ore 0]
    (cond
      (nil? reagent)
      ore
      (= reagent "ORE")
      (recur (second (first backlog)) (ffirst backlog) (rest backlog) leftover (+ ore reagent-qty))
      :else
      (let [r-needed (- reagent-qty (get leftover reagent 0))
            react-times (Math/ceil (/ r-needed (get-in reactions [reagent :num])))
            add-to-backlog (map (fn [x] [(* react-times (first x)) (second x)]) (get-in reactions [reagent :ingredients]))
            new-backlog (concat backlog add-to-backlog)]
        (recur (second (first new-backlog)) (ffirst new-backlog) (rest new-backlog)
               (assoc leftover reagent
                       (- (* react-times
                             (get-in reactions [reagent :num]))
                          r-needed)) ore)))))
