(ns aoc2019.grid)




(defn default-passable? [_ _]
  true)

(defn adjacent-locs [_ loc]
  (map #(+ loc %) [[0 -1] [0 1] [-1 0] [1 0]]))

(defn passable-adjacent-locs [grid loc]
  (filter (:passable? grid) (adjacent-locs grid loc)))

(defn adjacent? [grid loc1 loc2]
  (some #(= loc1 %) (adjacent-locs grid loc2)))

(def base-grid {
                :locations {}
                :passable? default-passable?
                })

(defn set-node [grid node loc]
  (assoc-in grid [:locations loc] node))

(defn distsqr [loc1 loc2]
  (let [[x1 y1] loc1
        [x2 y2] loc2]
    (+ (Math/pow (- x2 x1) 2) (Math/pow (- y2 y1) 2))))

(defn findpath [grid home destination])
