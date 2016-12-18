(ns day17)

;; Shamefully stolen from: https://gist.github.com/jizhang/4325757
(import 'java.security.MessageDigest
        'java.math.BigInteger)

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(defn move [[x y] dir]
  (cond
    (= dir \U) [x (dec y)]
    (= dir \D) [x (inc y)]
    (= dir \L) [(dec x) y]
    (= dir \R) [(inc x),y]))

(defn moveto [path]
  (let [pos [0 0]]
    (reduce move pos path)))

(defn tryexit [x y [test dir]]
  (cond
    (>= (int \a) (int test)) nil
    (and (= dir \U) (> y 0)) \U
    (and (= dir \D) (< y 3)) \D
    (and (= dir \L) (> x 0)) \L
    (and (= dir \R) (< x 3)) \R
    :else nil))

(defn exits [path input]
  (let [[x y] (moveto path)
        curnode (str input path)
        ff (take 4 (md5 curnode))
        gg "UDLR"
        ]
    (remove nil? (map #(tryexit x y %) (map vector ff gg)))))

(defn solved? [path]
  (let [dest (moveto path)
        [x y] dest]
    (and (= x 3) (= y 3))))

(defn queue [& vals]
  (apply merge (clojure.lang.PersistentQueue/EMPTY) vals))

(defn findpath [input]
  (loop [tq (queue "")]
      (let [path (peek tq)
            ex (exits path input)]
        (cond
          (solved? path) path
          (empty? ex) (recur (pop tq))
          :else
          (recur
            (apply conj (pop tq) (map #(str path %) ex)))))))

(defn findlongest [input]
  (loop [tq (queue "")
         len 0]
    (if (= 0 (count tq))
      len
      (let [path (peek tq)
            ex (exits path input)]
        (cond
          (solved? path) (recur (pop tq) (max len (count path)))
          (empty? ex) (recur (pop tq) len)
          :else
          (recur
            (apply conj (pop tq) (map #(str path %) ex)) len))))))