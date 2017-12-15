(ns aoc2017)
(require '[clojure.string :as str])

(defn find-things [needle haystack]
  (keep-indexed #(when (= %2 needle) %1) haystack))

(defn find-thing [needle haystack]
  (first (find-things needle haystack)))

(defn tf [ll]
  (loop [n (apply max ll)
         i (find-thing n ll)
         l (assoc! (transient ll) i 0)]
    (if (= 0 n)
      (persistent! l)
      (let [im (mod (inc i) (count l))]
        (recur (dec n) (inc i) (assoc! l im (inc (l im))))))))

(defn mm [ll]
  (loop [cur ll
         lls  [ll]
         c   0]
    (let [l (tf cur)]
      (if (some #(= l %) lls)
        (conj lls l)
        (recur l (conj lls l) (inc c))))))

(defn part1 [ll] (dec (count (mm ll))))

(defn part2 [ll] (let [ans (mm ll)]
                   (find-things (last ans), ans)))

(part1 [0 2 7 0])
