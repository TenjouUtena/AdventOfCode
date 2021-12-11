(ns aoc2021-clj.10
  (:require [clojure.string :as s]))


(defn make-input [filen] (s/split-lines (slurp (str "resources/" filen))))

(def errval {
             \) 3
             \] 57
             \} 1197
             \> 25137
             })

(def aerrval {
              \) \1
              \] \2
              \} \3
              \> \4
              })

(def opens [\( \{ \[ \<])
(def closes [\) \} \] \>])
(def zmatch {\( \)
             \{ \}
             \[ \]
             \< \>
             })

(defn determine-score [lo]
  (BigInteger. ^String
    (->> lo
           (map #(get zmatch %))
           (map #(get aerrval %))
           (apply str)
           )
    5)
  )

(defn corruption-value
  ([nav]
   (corruption-value nav false))
  ([nav part2]
   (loop [rn nav
          stack '()]
     (let [curb (first rn)
           curs (first stack)]
       (cond
         (empty? rn)
         (if part2 (determine-score stack) 0)
         (empty? stack)
         (recur (rest rn) (conj stack (first rn)))
         (some #(= curb %) opens)
         (recur (rest rn) (conj stack curb))
         (= (get zmatch curs) curb)
         (recur (rest rn) (pop stack))
         :otherwise
         (if part2 0 (get errval curb))
         )))))

(defn part1 [i]
  (reduce + (map corruption-value i)))

(defn part2 [i]
  (let [ff (sort (filter #(not (zero? %)) (map #(corruption-value % true) i)))]
    (nth ff (/ (dec (count ff)) 2))))