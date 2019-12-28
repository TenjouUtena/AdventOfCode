(ns aoc2019.22)




(defn take-with-incr [deck incr size]
  (map first (take size (iterate #(drop (- size incr) %) (cycle deck)))))

(defn cut [deck incr size]
  (let [t (if (< 0 incr) incr (+ size incr))]
    (concat (drop t deck) (take t deck))))

