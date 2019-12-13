(ns aoc2019.11
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]))


(def input (s/trim (slurp "resources/11-chewy2.txt")))

(def machine (create-machine-spec input ))


(def dirs {
           "U" {:left "L" :right "R" :vec [0 -1]}
           "L" {:left "D" :right "U" :vec [-1 0]}
           "D" {:left "R" :right "L" :vec [0  1]}
           "R" {:left "U" :right "D" :vec [1  0]}
           })

(def def-node {:painted false
               :color 0})

(defn run-robot [start]
  (loop [state (run-machine-with-input machine [start])
         dir "U"
         loc [0 0]
         world {[0 0] def-node}]
    (cond
      (and (not (:halt state)) (not (:inputwait state)))
      [state world]
      (:halt state)
      world
      :else
      (let [curgrid (-> (get world loc def-node)
                        (assoc :painted true)
                        (assoc :color (first (:outputstream state))))
            newdir (get-in dirs [dir (if (= 0 (last (:outputstream state))) :left :right)])
            newloc (map + loc (:vec (get dirs newdir)))
            newgrid (get world newloc def-node)]
        (recur (run-machine-base (-> state
                                     (assoc :inputwait false)
                                     (assoc :outputstream [])
                                     (assoc :inputstream [(:color newgrid)])))
               newdir
               newloc
               (-> world
                   (assoc loc curgrid)
                   (assoc newloc newgrid)))))))




(defn main1 []
  (count (run-robot 0)))

(defn main2 []
  (map #(reduce str %) (partition 43 (let [run (run-robot 1)]
                                       (for [y (range 0 6)
                                             x (range 0 43)]
                                         (if (= 1 (get-in run [[x y] :color] 0)) "*" " "))))))
