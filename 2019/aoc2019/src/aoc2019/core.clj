(ns aoc2019.core
  (:require [clojure.string :as str]))


(defn read-file-with-map
  [f ff]
  (map ff (str/split-lines (slurp f)))
  )


(defn read-file-as-strings
  [f]
  (read-file-with-map f identity))

(defn read-file-as-ints
  [f]
  (read-file-with-map f #(Integer/parseInt %)))
