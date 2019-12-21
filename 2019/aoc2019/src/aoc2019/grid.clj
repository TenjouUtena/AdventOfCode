(ns aoc2019.grid)




(defn default-passable? [_ _]
  true)

(defn adjacent-locs [grid loc]
  (keys (select-keys (:locations grid) (map #(mapv + loc %) [[0 -1] [0 1] [-1 0] [1 0]]))))

(defn passable-adjacent-locs [grid loc]
  (filter #((:passable? grid) grid %) (adjacent-locs grid loc)))

(defn adjacent? [grid loc1 loc2]
  (some #(= loc1 %) (adjacent-locs grid loc2)))

(def base-grid {
                :locations {}
                :passable? default-passable?
                })

(defn set-node [grid node loc]
  (assoc-in grid [:locations loc] node))

(defn get-node [grid loc]
  (get-in grid [:locations loc]))

(defn distsqr [loc1 loc2]
  (let [[x1 y1] loc1
        [x2 y2] loc2]
    (+ (Math/pow (- x2 x1) 2) (Math/pow (- y2 y1) 2))))

(defn findpath [grid home destination]
  (loop [open #{home}
         from {}
         g {home 0}
         f {home (distsqr home destination)}]
    (if (empty? open)
      nil
      (let [curr (apply min-key #(get f %) open)
            nis (filter #(< (inc (get g curr)) (get g % 9999999)) (passable-adjacent-locs grid curr))]
        (if
          (= curr destination)
          (reverse (take-while some? (iterate #(get from %) curr)))
          (recur
           (clojure.set/union (clojure.set/difference open (set [curr])) (set nis))
           (into from (map (fn [x] {x curr}) nis))
           (into g (map (fn [x] {x (inc (get g curr))}) nis))
           (into f (map (fn [x] {x (+ (inc (get g curr)) (distsqr x destination))}) nis))))))))


(def dirs {
           [0 -1] :n
           [0 1] :s
           [1 0] :e
           [-1 0] :w
           })

(defn translate-path-to-dirs [path]
  (map (fn [x] (get dirs (mapv - (second x) (first x)))) (partition 2 1 path)))
