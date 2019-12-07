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
    (get system planet (new-planet planet)))


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
    (zipmap (keys s) (map #(count-level (val %) s) s))))

(def system (count-levels input))

(defn hierarchy [planet system]
  (map (fn [x] (:name x)) (take-while some? (iterate #(get system (:parent %)) (get system planet)))))

(def answer1 (reduce + (map (fn [x] (:level (val x))) (count-levels input))))

(def answer2
  (- (count (filter #(= (val %) 1) (frequencies (into (hierarchy "YOU" system) (hierarchy "SAN" system))))) 2))
