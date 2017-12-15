(ns aoc2017)


(defn stream [] (slurp "9.txt"))


(defn skip [stream]
  (rest (rest stream)))

(defn garbage [stream]
  (loop
      [stream stream]
    (let [cc (first stream)]
      (cond
        (= cc \!)
        (recur (skip stream))
        (= cc \>)
        (rest stream)
        :else
        (recur (rest stream))))))

(defn garbage2 [stream]
  (loop
      [stream stream
       score 0]
    (let [cc (first stream)]
      (cond
        (= cc \!)
        (recur (skip stream) score)
        (= cc \>)
        [(rest stream) score]
        :else
        (recur (rest stream) (inc score))))))


(defn score [stream]
  (loop [stream stream
         nest 0
         score 0]
    (let [cc (first stream)]
      (cond
        (= cc \})
        (recur (rest stream) (dec nest) (+ score nest))
        (= cc \{)
        (recur (rest stream) (inc nest) score)
        (= cc \<)
        (recur (garbage stream) nest score)
        (= cc nil)
        score
        :else
        (recur (rest stream) nest score)))))

(defn score2 [stream]
  (loop [stream stream
         score 0]
    (let [cc (first stream)]
      (cond
        (= cc \<)
        (let [gg (garbage2 (rest stream))]
          (recur (gg 0) (+ score (gg 1))))
        (= cc nil)
        score
        :else
        (recur (rest stream) score)))))
