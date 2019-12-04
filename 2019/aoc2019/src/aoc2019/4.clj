(ns aoc2019.4)

(defn main1 [min max]
  (filter #(and (apply <= (map int (str %)))
                (< (count (distinct (str %))) (count (str %)))
                (some (fn [x] (= 2 (val x))) (frequencies (str %))))
          (range min max)))
