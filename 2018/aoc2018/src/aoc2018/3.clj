(ns aoc2018.3
  (:require [clojure.string :as str]))

(defn get-stuff []
  (str/split-lines (slurp "resources/3.txt")))

(defn create-room [roomspec]
  (zipmap [:number :x :y :width :height] (map read-string (rest (re-matches #"#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)" roomspec)))))

(defn- is-in-room
  [ind_x ind_y room]
  (and (>= ind_x (:x room))
             (< ind_x (+ (:x room) (:width room)))
             (>= ind_y (:y room))
             (< ind_y (+ (:y room) (:height room)))))

(defn is-in-index [room size index]
  (let [ind_x (mod index size)
        ind_y (quot index size)]
    (if (is-in-room ind_x ind_y room)
      1
      0)))

(defn do-map-room [w h room]
  (map (partial is-in-index room w) (range (* w h)))
  )

(defn print-map [map w h]
  (doseq [r (range h)]
    (print (take w (drop (* r w) map)) "\n")))


(defn do-map [w h]
  (apply (partial map +) (pmap (partial do-map-room w h) (map create-room (get-stuff)))))

(defn countbad [w h]
  (count (filter (partial < 1) (do-map w h))))

(defn create-indexes [size room]
  (for [x (range (:x room) (+ (:x room) (:width room)))
        y (range (:y room) (+ (:y room) (:height room)))
        ]
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
  (let [rooms (gen-rooms w)
        mm (create-all-indexes w h)]
    ;(print (room-sum (nth rooms 1) mm) (count (nth rooms 1)) (take 10 mm))
    ;(count (filterv #(= (count %) (room-sum % mm)) rooms))
    (for [n (range (count rooms))]
      (let [x (nth rooms n)]
        (if
            (= (count x) (room-sum x mm))
          (inc n)
          nil)))
  ))
