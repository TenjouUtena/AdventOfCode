(ns aoc2017)



(def seqq (range 1 570))

(reduce + (map #(apply + %) (map vector seqq seqq)))






