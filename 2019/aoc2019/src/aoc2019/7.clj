(ns aoc2019.7
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]
            [clojure.math.combinatorics :as combo]
            ))

(def input-t "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0")

(def input-r "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")

(def input (s/trim (slurp "resources/7.txt")))

(def machine (create-machine-spec input))

(defn run-stages [inputs]
  (loop [ip (first inputs)
         ri (rest inputs)
         is 0]
    (if (some? ip)
      (recur (first ri) (rest ri) (first (:outputstream (run-machine-with-input machine [ip is]))))
      is)))


(defn run-main-2 [inputs]
  (let [m0 (assoc (create-state-from-mem machine) :inputstream [(nth inputs 0) 0])
        m1 (assoc (create-state-from-mem machine) :inputstream [(nth inputs 1)])
        m2 (assoc (create-state-from-mem machine) :inputstream [(nth inputs 2)])
        m3 (assoc (create-state-from-mem machine) :inputstream [(nth inputs 3)])
        m4 (assoc (create-state-from-mem machine) :inputstream [(nth inputs 4)])
        ]
    (loop [current-machine m0
           rest-machines [m1 m2 m3 m4]]
      (let [new-machine-state (run-machine-base current-machine)
            new-rest (if (:halt new-machine-state)
                       rest-machines
                       (conj rest-machines (assoc new-machine-state :inputwait false)))]
        (if (empty? rest-machines)
          (last (:outputstream new-machine-state))
          (recur (update (first new-rest) :inputstream conj (last (:outputstream new-machine-state)))
                 (vec (rest new-rest)))
          )))))


(def answer1
  (first (sort #(> (nth %1 1) (nth %2 1)) (map #(identity [% (run-stages %)]) (combo/permutations [0 1 2 3 4])))))

(defn answer2 [x]
  (first (sort #(> (nth %1 1) (nth %2 1)) (map #(identity [% (run-main-2 %)]) (combo/permutations x)))))
