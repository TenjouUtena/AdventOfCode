(ns aoc2019.12)



(def system-t1 [
             [-1 0 2]
             [2 -10 -7]
             [4 -8 8]
             [3 5 -1]
             ])

;<x=-8, y=-10, z=0>
;<x=5, y=5, z=10>
;<x=2, y=-7, z=3>
;<x=9, y=-8, z=-3>


(def system-t2 [
                 [-8 -10 0]
                 [5 5 10]
                 [2 -7 3]
                 [9 -8 -3]
                 ])

;<x=1, y=3, z=-11>
;<x=17, y=-10, z=-8>
;<x=-1, y=-15, z=2>
;<x=12, y=-4, z=-4>

(def system [
             [1 3 -11]
             [17 -10 -8]
             [-1 -15 2]
             [12 -4 -4]
             ])


(defn calculate-velocities [axis]
  (map (fn [y] (reduce + y))
       (map (fn [[first & rest]]
               (map #(cond (< first %) 1 (> first %) -1 :else 0) rest))
            (take (count axis) (partition (count axis) 1 (cycle axis))))))




(defn absolute-sum [sys]
  (map (fn [x] (reduce (fn [y z] (+ (Math/abs y) (Math/abs z))) x)) sys))


(defn calc-energy [sys vel]
  (reduce + (map * (absolute-sum sys) (absolute-sum vel))))

(defn calculate-step [system steps]
  (loop [sys system
         vel (take (count system) (repeat [0 0 0]))
         step 0]
    (let [ndv (apply mapv vector (mapv calculate-velocities (apply mapv vector sys)))
           nv (mapv #(mapv + %1 %2) vel ndv)
          ns (mapv #(mapv + %1 %2) sys nv)]
      (if (>= step steps)
        [sys vel (calc-energy sys vel)]
        (recur ns nv (inc step))))
    ))


(defn calculate-step-zero [system axis]
  (loop [sys system
         vel (take (count system) (repeat [0 0 0]))
         step 0]
    (let [ndv (apply mapv vector (mapv calculate-velocities (apply mapv vector sys)))
          nv (mapv #(mapv + %1 %2) vel ndv)
          ns (mapv #(mapv + %1 %2) sys nv)]
      (if (every? #(= 0 %) (nth (apply mapv vector nv) axis))
        (inc step)
        (recur ns nv (inc step))))
    ))


(defn gcd
  [a b]
  (if (zero? b)
    a
    (recur b, (mod a b))))

(defn lcm
  [a b]
  (/ (* a b) (gcd a b)))


