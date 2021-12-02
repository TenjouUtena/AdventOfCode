(ns aoc2021-clj.2
  (:require [clojure.string :as s]))

(def inp (s/split-lines (slurp "resources/2.txt")))

(def mapinst {
              "forward" [1 0]
              "up" [0 -1]
              "down" [0 1]
              })

(defn part1 [i]
  (loop [pos [0 0]
         inst i]
    (if (empty? inst) pos
                      (let [curinst (s/split (first inst) #" ")]
                        (recur (map + pos
                                    (map * (get mapinst (first curinst))
                                         (repeat (bigint (second curinst)))
                                         ))
                               (rest inst))))))

(defn part2 [i]
  (loop [pos [0 0 0]
         inst i]
    (if (empty? inst) pos
                      (let [curinst (s/split (first inst) #" ")
                            mag (bigint (second curinst))]
                        (recur (map + pos
                                    (concat (map * (get mapinst (first curinst))
                                               (repeat mag))
                                            (vector (if (= "forward" (first curinst))
                                                      (* mag (second pos))
                                                      0))))
                               (rest inst))))))

