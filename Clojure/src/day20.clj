(ns day20
  (:require [clojure.string :as s])
  )



(defn makenum [pair]
  (let [f (s/split pair #"-")]
    (conj [] (bigint (first f)) (bigint (nth f 1)))))

(defn getpairs [filename]
  (sort (map makenum (s/split (slurp filename) #"\r\n"))))

(defn normalize [lon]
  (loop [[a1 a2] (first lon)
         [n1 n2] (nth lon 1)
         r (drop 2 lon)
         fin []]
    (cond
      (empty? r)
       (conj fin [a1 a2])
      :else
       (if (<= n1 (inc a2))
         (recur
            [a1 (max a2 n2)] (first r) (rest r) fin)
         (recur
            [n1 n2] (first r) (rest r) (conj fin [a1 a2]))))))

(defn countn [lon]
  (loop [[a1 a2] (first lon)
         [n1 n2] (nth lon 1)
         acc 0
         r (drop 2 lon)]
    (if (empty? r)
      (+ acc (- n1 (inc a2)))
      (recur
        [n1 n2] (first r) (+ acc (- n1 (inc a2))) (rest r)))))

