(ns aoc2019.6
  (:require [clojure.string :as str]))


(defn orbit [s]
  (let [m (re-matches #"([0-9A-Z]+)\)([0-9A-Z]+)" s)]
    [(nth m 1) (nth m 2)]))


(def input (map orbit (str/split-lines (slurp "resources/6.txt"))))



(defn new-planet [name]
  {:name name
   :parent nil
   :children []
   :level 0
   })

(defn get-or-create-planet [system planet]
  (if (some #(= planet %) (keys system))
    (get system planet)
    (new-planet planet)))


(defn create-system-from-orbits [orbits]
  (loop [o (first orbits)
         r (rest orbits)
         system {}]
    (let [p1 (update (get-or-create-planet system (first o)) :children conj (nth o 1))
          p2 (assoc (get-or-create-planet system (nth o 1)) :parent (first o))
          ns (-> system
                 (assoc (:name p1) p1)
                 (assoc (:name p2) p2))]
      (if (empty? r)
        ns
        (recur (first r) (rest r) ns)))))

(defn count-level [planet system]
  (assoc planet :level (loop [l 0
                              n planet]
                         (if (nil? (:parent n))
                           l
                           (recur (inc l) (get system (:parent n)))))))


(defn count-levels [input]
  (let [s (create-system-from-orbits input)]
    (loop [p (first (keys s))
           r (rest (keys s))
           sy s]
      (let [ns (assoc sy p (count-level (get sy p) sy))]
        (if (empty? r)
          ns
          (recur (first r) (rest r) ns))))))

(def system (count-levels input))

(defn hierarchy [planet system]
  (loop [p (get system planet)
         path []]
    (if (nil? (:parent p))
      (conj path (:name p))
      (recur (get system (:parent p)) (conj path (:name p))))))



(def answer2
  (count (filter #(= (val %) 1) (frequencies (into (hierarchy "YOU" system) (hierarchy "SAN" system))))))
