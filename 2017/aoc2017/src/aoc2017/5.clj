(ns aoc2017)
(require '[clojure.string :as str])
(require 'clojure.tools.trace)

(defn makelist [] (mapv #(Integer. %)  (str/split-lines (slurp "5.txt"))))
(defn part1
    [inst]
    (loop [ip 0
           inst inst
           c 0]
      (if
          (or (< ip 0) (> ip (dec (count inst)))) 
          c
          (recur (+ ip (inst ip)) 
                 (assoc inst ip (inc (inst ip))) 
                 (inc c)))))

(defn part2
    [inst]
    (loop [ip 0
           inst (transient inst)
           c 0]
      (if
          (or (< ip 0) (> ip (dec (count inst)))) 
          c
          (recur (+ ip (inst ip)) 
                 (assoc! inst ip (if (> (inst ip) 2) 
                                          (dec (inst ip)) 
                                          (inc (inst ip)))) 
                 (inc c)))))
