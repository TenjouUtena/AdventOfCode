(ns aoc2019.7
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]
            [clojure.math.combinatorics :as combo]
            [clojure.core.async :as a]))

(def input-t "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0")

(def input "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")

(def input-r (s/trim (slurp "resources/7.txt")))

(def machine (create-machine-spec input))

(defn run-stages [inputs]
  (loop [ip (first inputs)
         ri (rest inputs)
         is 0]
    (if (some? ip)
      (recur (first ri) (rest ri) (first (:outputstream (run-machine-with-input machine [ip is]))))
      is)))

;(def answer1
;  (first (sort #(> (nth %1 1) (nth %2 1)) (map #(identity [% (run-stages %)]) (combo/permutations [0 1 2 3 4])))))


(defn run-main-2 [inputs]
  (let [inchan (a/chan 2)
        m0 (run-machine-with-chan machine inchan)
        m1 (run-machine-with-chan machine m0)
        m2 (run-machine-with-chan machine m1)
        m3 (run-machine-with-chan machine m2)
        m4 (run-machine-with-chan machine m3)]
    (a/put! m0 (nth inputs 1))
    (a/put! m1 (nth inputs 2))
    (a/put! m2 (nth inputs 3))
    (a/put! m3 (nth inputs 4))
    (a/put! inchan (nth inputs 0))
    (a/>!! inchan 0)
    (loop [passnum (a/<!! m4)]
      (a/>!! inchan passnum)
      (let [p2 (a/<!! m4)]
        (if (nil? p2)
          passnum
          (recur p2))))))


(defn create-machine-w-channels []
  (let [inp  (a/chan 2)
        outp (a/chan 3)]
    [inp outp (a/thread (run-machine-with-chan machine inp outp))]))


(defn run-main-2c [inputs]
  (let [
        [i0 o0 g0] (create-machine-w-channels)
        [i1 o1 g1] (create-machine-w-channels)
        [i2 o2 g2] (create-machine-w-channels)
        [i3 o3 g3] (create-machine-w-channels)
        [i4 o4 g4] (create-machine-w-channels)
        ]
    (a/>!! i0 (nth inputs 0))
    (a/>!! i1 (nth inputs 1))
    (a/>!! i2 (nth inputs 2))
    (a/>!! i3 (nth inputs 3))
    (a/>!! i4 (nth inputs 4))
    (a/pipe o0 i1)
    (a/pipe o1 i2)
    (a/pipe o2 i3)
    (a/pipe o3 i4)
;    (a/pipe o4 i0)
    (a/>!! i0 0)
    (a/<!! g3)
    (a/<!! o4)
    ))


(defn run-main-2a [inputs]
  (let [inchan (a/chan 10)
        m0 (run-machine-with-chan machine inchan)
        m1 (run-machine-with-chan machine m0)
        m2 (run-machine-with-chan machine m1)
        m3 (run-machine-with-chan machine m2)
        m4 (run-machine-with-chan machine m3)]
    (println "Machines made!")
    (a/>!! m0 (nth inputs 1))
    (a/>!! m1 (nth inputs 2))
    (a/>!! m2 (nth inputs 3))
    (a/>!! m3 (nth inputs 4))
    (a/>!! inchan (nth inputs 0))
    (println "machines primed")
    (a/>!! inchan 0)
    (println "run a machine")
    (println "run machine ")
    (println "Waitinf for complete")
    (a/<!! m4)))



(defn answer2 [x]
  (first (sort #(> (nth %1 1) (nth %2 1)) (map #(identity [% (run-main-2c %)]) (combo/permutations x)))))
