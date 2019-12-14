(ns aoc2019.14
  (:require [clojure.string :as s]))


(def input (s/trim (slurp "resources/14.txt")))

(defn extract-reaction [react]
  (let [mm (re-matcher #"(\d+) ([A-Z]+)" react)]
    (loop [retval []
           m1 (re-find mm)]
      (if m1
        (recur (conj retval [(nth (bigint (re-groups mm)) 1) (nth (re-groups mm) 2)])
               (re-find mm))
        {(last (last retval)) {:num (first (last retval))
                               :ingredients (butlast retval)}}))))

(def reactions (map extract-reaction (s/split-lines input)))


(defn create-reagent
  ([reagent amount]
   (create-reagent reagent amount {} 0))
  ([reagent amount balance ore]
   (if
       (= reagent "ORE")
     [(+ ore amount) balance]
     (loop [sub-reagent (first (get-in reactions [reagent :ingredients]))
            other (rest (get reactions [reagent :ingredients]))
            newore 0
            bal balance]
       (if sub-reagent
         (let [[no2 newb] (create-reagent (second sub-reagent) (first sub-reagent) balance newore)]
           (recur (first other) (rest other) newore (concat newb bal)))
         newore)))))

