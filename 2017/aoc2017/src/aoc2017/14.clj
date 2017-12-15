(ns aoc2017
  (:require [clojure.string :as str]))
(require '[clojure.string :as str])

(def inst [129 154 49 198 200 133 97 254 41 6 2 1 255 0 191 108])

(defn replaceloop [seq seq2 offset]
  (loop [
         seq (transient seq)
         seq2 seq2
         location 0
         ]
    (if
      (= seq2 [])
      (persistent! seq)
      (recur (assoc! seq (mod (+ location offset) (count seq)) (first seq2)) (rest seq2) (inc location)))))


(defn loop1 [ll cmds]
  (loop [ll ll
         cmds cmds
         loc 0
         skip 0]
    (let [
          cmd (first cmds)

          ]
      (if
        (= cmd nil)
        ll
        (recur (replaceloop ll
                            (reverse (take cmd (drop loc (apply into (repeat 2 ll)))))
                            loc)
               (rest cmds)
               (mod (+ loc skip cmd) (count ll))
               (inc skip))))))



;(loop1 [0 1 2 3 4] [3 4 1 5])
;(defn sparsehash [input]
;  (nth (iterate #(loop1 % input) (vec(range 256))) 63))

(defn sparsehash [input]
  (loop1 (vec(range 256)) (reduce into (repeat 64 input))))

(defn densehash [input]
  (map #(reduce bit-xor %) (partition 16 (sparsehash input))))

(defn hexize [ll]
  (clojure.string/join "" (map #(format "%02x" %) ll)))

(defn hexh [sss]
  (hexize (densehash (vec (into (mapv int sss) [17 31 73 47 23])))))

(defn binconv [cc]
  (cond
    (= cc \0) "0000"
    (= cc \1) "0001"
    (= cc \2) "0010"
    (= cc \3) "0011"
    (= cc \4) "0100"
    (= cc \5) "0101"
    (= cc \6) "0110"
    (= cc \7) "0111"
    (= cc \8) "1000"
    (= cc \9) "1001"
    (= cc \a) "1010"
    (= cc \b) "1011"
    (= cc \c) "1100"
    (= cc \d) "1101"
    (= cc \e) "1110"
    (= cc \f) "1111"
    ))

(defn binhash [ss]
  (clojure.string/join (map binconv (hexh ss))))

(defn makegrid [ss]
  (map #(binhash %) (map (fn [x] (str/join "" [ss, "-", x])) (map #(format "%d" %)(range 128)))))


(defn day1 [ss]
  (count (filter #(= \1 %) (str/join "" (makegrid ss)))))

