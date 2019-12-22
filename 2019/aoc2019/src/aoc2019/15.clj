(ns aoc2019.15
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]
            [lanterna.screen :as scr]
            [aoc2019.grid :as grid]))

(def input (s/trim (slurp "resources/15.txt")))

(def machine (create-machine-spec input))

(defn passable [grid loc]
  (not (= \# (:tile (grid/get-node grid loc)))))

(def dirc {:n 1 :s 2 :e 4 :w 3})
(def dirv {:n [0 -1]
           :s [0 1]
           :e [1 0]
           :w [-1 0]})

(defn makebacklog [loc]
  (map (fn [x] [loc x]) (keys dirc)))

(defn find-goal []
  (loop [loc [0 0]
         backlog (makebacklog loc)
         machine (run-machine-with-input machine [(get dirc (second (first backlog)))])
         m (-> grid/base-grid
               (assoc :passable? passable)
               (grid/set-node {:tile \.} loc))
         tried []]
    (let [code (last (:outputstream machine))
          newtile (case code 0 \# 1 \. 2 \@)
          tryloc (mapv + (ffirst backlog) (get dirv (second (first backlog))))
          newloc (case code 0 (ffirst backlog) 1 tryloc 2 tryloc)
          newbacklog (if (and (= code 1) (not-any? #(= newloc %) tried))
                       (concat (makebacklog newloc) (rest backlog))
                       (rest backlog))
          nextdest (ffirst newbacklog)
          newpath (if (empty? newbacklog)  nil
                      (map #(get dirc %) (conj (vec (grid/translate-path-to-dirs
                                                     (grid/findpath m newloc nextdest)))
                                               (second (first newbacklog)))))]
      (if (empty? newbacklog) ;(or (> -100 (first loc)) (= code 2))
        [m tryloc newloc] ;(count (grid/translate-path-to-dirs (grid/findpath m [0 0] tryloc)))
        (recur newloc newbacklog
               (run-machine-base (-> machine
                                     (assoc :outputstream [])
                                     (assoc :inputstream newpath)
                                     (assoc :inputwait false)))
               (grid/set-node m {:tile newtile} tryloc)
               (conj tried newloc))))))


(defn otime []
  (let [mm (first (find-goal))
        om [-20 -12]
        open-tiles (filter #(= (:tile (grid/get-node mm %)) \.) (keys (:locations mm)))]
    (apply max (map #(count (grid/findpath mm % om)) open-tiles))))
