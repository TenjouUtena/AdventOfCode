(ns aoc2021-clj.4
  (:require [clojure.string :as s]
            [clojure.set :as st]))

(defn make-board [rawboard]
  (let [rows (mapv (fn [x] (map bigint
                                (rest
                                  (re-find #"\W*(\d+)\W+(\d+)\W+(\d+)\W+(\d+)\W+(\d+)" x))))
                   rawboard)
        cols (apply mapv vector rows)]
    {:rawrows rows
     :rawcols cols
     :rows    (map set rows)
     :cols    (map set cols)
     :checks  (map set (concat rows cols))
     :allnums (reduce into #{} (concat rows cols))
     }
    ))

(defn make-input [inp]
  (let [raw1 (s/split-lines (slurp (str "resources/" inp)))
        rawboards (partition 5 (filter not-empty (rest raw1)))]
    {
     :numbers (mapv bigint (s/split (first raw1) #","))
     :rawboards rawboards
     :boards (map make-board rawboards)
     }))

(defn check-board [board nums]
  (some #(st/subset? % (set nums)) (:checks board)))

(defn part1 [inp]
  (loop [check (take 5 (:numbers inp))
         count 5]
    (let [match (filter #(check-board % check) (:boards inp))]
      (if (not-empty match)
        ;;match
        (* (last check) (reduce + (st/difference (:allnums (first match)) (set check))))
        (recur (take (inc count) (:numbers inp)) (inc count))))))

(defn part2 [inp]
  (loop [check (take 5 (:numbers inp))
         boards (:boards inp)
         counter 5]
    (let [match (filter #(check-board % check) boards)]
      (if (and (not-empty match) (= 1 (count boards)))
        ;;match
        (* (last check) (reduce + (st/difference (:allnums (first match)) (set check))))
        (recur (take (inc counter) (:numbers inp))
               (filter #(not (check-board % check)) boards)
               (inc counter))))))

