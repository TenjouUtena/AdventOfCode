(ns aoc2019.15
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]
            [lanterna.screen :as scr]
            [aoc2019.grid :as grid]))

(def input (s/trim (slurp "resources/15.txt")))

(def machine (create-machine-spec input))

(defn passable [grid loc]
  (= \. (:wall (grid/get-node grid loc))))


(defn find-goal [machine]
  (loop []))
