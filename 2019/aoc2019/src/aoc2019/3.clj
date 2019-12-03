(ns aoc2019.3
  (:require [clojure.string :as str]
            [aoc2019.core :refer :all]))



;(def real-wires (map str/split (read-file-as-strings "resources/3.txt") #","))

(def test-wire-1 (str/split "R8,U5,L5,D3", #","))

(defn new-point [point dir dist]
  (let [[x y] point]
    (case dir
      "U"
      [x (- y dist)]
      "D"
      [x (+ y dist)]
      "R"
      [(+ x dist) y]
      "L"
      [(- x dist) y]
      )))

(defn make-segments [wires]
  (loop [segments []
         from [0 0]
         inst (first wires)
         remain (rest wires)]
    (let [g (re-matches #"([A-Z])([0-9]+)" inst)
          dir (g 1)
          dist (Integer/parseInt (g 2))
          np (new-point from dir dist)
          ]
      (println segments inst remain g dir dist)
      (if (seq remain)
        (recur (conj segments {:p1 from :p2 np :dir dir}) np (first remain) (rest remain))
        segments))))


