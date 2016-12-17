(ns day15)

;; This is a list of discs with [position start-offset] so the input + order in list

(def discs [[13 11] [5 1] [17 13] [3 3] [7 6] [19 22] [11 6]])

;; (def discs [[5 4] [2 2]])

(defn calctime [[num sta] time]
  (mod (+ 1 time sta) num))

(defn solven [time]
   (apply + (map #(calctime % time) discs)))

(defn cc []
   (first (drop-while #(not (= 0 (solven %))) (iterate #(+ 1 %) 0))))
