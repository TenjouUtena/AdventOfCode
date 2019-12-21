(ns aoc2019.15
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]
            [lanterna.screen :as scr]))

(def input (s/trim (slurp "resources/15.txt")))

(def machine (create-machine-spec input))

