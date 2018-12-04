(ns aoc2018.3
  (:require [clojure.string :as str]))

(defn get-stuff []
  (str/split-lines (slurp "resources/3.txt")))

(defn create-room [roomspec]
  (zipmap [:number :x :y :width :height] (map read-string (rest (re-matches #"#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)" roomspec)))))

(defn create-indexes [size room]
  (for [x (range (:x room) (+ (:x room) (:width room)))
        y (range (:y room) (+ (:y room) (:height room)))]
    (+ (* y size) x)))

(defn- gen-rooms
  [w]
  (map (partial create-indexes w) (map create-room (get-stuff))))

(defn create-all-indexes [w h]
  (reduce #(conj %1 {%2 (inc (get %1 %2 0))}) {} (reduce concat (gen-rooms w))))

(defn new-day3-1 [w h]
  (count (filter #(> (second %) 1) (create-all-indexes w h))))

(defn room-sum [room mm]
  (reduce + (map (partial get mm) room)))

(defn day-3-2 [w h]
  (filter (comp not nil?)
          (let [rooms (gen-rooms w)
                mm (create-all-indexes w h)]
            (for [n (range (count rooms))]
              (let [x (nth rooms n)]
                (if
                 (= (count x) (room-sum x mm))
                  (inc n)
                  nil))))))
