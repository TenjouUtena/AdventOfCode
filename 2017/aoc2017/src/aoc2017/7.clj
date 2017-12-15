(ns aoc2017
  (:require [clojure.string :as str]))
(require '[clojure.string :as str])
(require '[clojure.set :as s])
(defn makelist [] (str/split-lines (slurp "7.txt")))

(def input (makelist))

(defn retmapnode [ll]
  (let
   [with-kids (re-matcher #"([a-z]+) \(([0-9]+)\) \-> ((?>[a-z]+(?>\, )?)+)" ll)
    without-kids  (re-matcher #"([a-z]+) \(([0-9]+)\)" ll)
    with-kids? (re-find with-kids)
    without-kids? (re-find without-kids)]
    (cond
      (some? with-kids?)
      {(nth (re-groups with-kids) 1) (str/split (nth (re-groups with-kids) 3) #"\, ")}
      (some? without-kids?)
      {(nth (re-groups without-kids) 1) []})))

(defn retweight [ll]
  (let [wf (re-matcher #"([a-z]+) \(([0-9]+)\)" ll)
        mm (re-find wf)]
    {(nth (re-groups wf) 1) (Integer. (nth (re-groups wf) 2))}))

(defn maketree []
  (reduce into (map retmapnode input)))

(defn makeweights []
  (reduce into (map retweight input)))

(defn weight [node weights tree]
  (let [
        kids (tree node)
        ww (weights node)
        ]
    (if (empty? kids)
      ww
      (let [doodoo (map #(weight % weights tree) kids)]
        (if (apply = doodoo)
          (+ ww (apply + doodoo))
          (println kids doodoo (map #(weights %) kids)))))))

(defn part1 []
  (let [tree (maketree)]
    (s/difference (set (keys tree)) (set (reduce into (vals tree))))))
