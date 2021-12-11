(ns aoc2021-clj.10
  (:require [clojure.string :as s]))


(defn make-input [filen] (s/split-lines (slurp (str "resources/" filen))))

(def errval {
             \) 3
             \] 57
             \} 1197
             \> 25137
             })

(def opens [\( \{ \[ \<])
(def closes [\) \} \] \>])
(def zmatch {\( \)
             \{ \}
             \[ \]
             \< \>
             })

(defn corruption-value [nav]
  (loop [rn nav
         stack '()]
    (let [curb (first rn)
          curs (first stack)]
      (cond
          (empty? rn)
          0
          (empty? stack)
          (recur (rest rn) (conj stack (first rn)))
          (some #(= curb %) opens)
          (recur (rest rn) (conj stack curb))
          (= (get zmatch curs) curb)
          (recur (rest rn) (pop stack))
          :otherwise
          (get errval curb)
          ))))

(defn part1 [i]
  (reduce + (map corruption-value i)))