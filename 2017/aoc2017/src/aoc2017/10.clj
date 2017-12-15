(ns aoc2017)

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

(defn day2 [sss]
  (hexize (densehash (vec (into (mapv int sss) [17 31 73 47 23])))))
