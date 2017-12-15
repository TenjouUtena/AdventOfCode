
(ns aoc2017)

(defn makepath [] (clojure.string/split (clojure.string/trim-newline (slurp "11.txt")) #"\,"))

(defn distance [x y]
  (+ (Math/abs y) (/ (Math/abs x) 2)))

(defn walkpath [path]
  (loop [path path
         x 0
         y 0
         maxdist 0]
    (let [inst (first path)
          parity (mod x 2)]
      (cond
        (= inst nil)
        [x y maxdist]

        (= inst "n")
        (recur (rest path) x (inc y) (max maxdist (distance x (inc y))))

        (= inst "s")
        (recur (rest path) x (dec y) (max maxdist (distance x (dec y))))

        (and (= inst "ne") (= parity 1))
        (recur (rest path) (inc x) (inc y) (max maxdist (distance (inc x) (inc y))))
        (= inst "ne")
        (recur (rest path) (inc x) y (max maxdist (distance (inc x) y)))

        (and (= inst "nw") (= parity 1))
        (recur (rest path) (dec x) (inc y) (max maxdist (distance (dec x) (inc y))))
        (= inst "nw")
        (recur (rest path) (dec x) y (max maxdist (distance (dec x) y)))

        (and (= inst "se") (= parity 0))
        (recur (rest path) (inc x) (dec y) (max maxdist (distance (inc x) (dec y))))
        (= inst "se")
        (recur (rest path) (inc x) y (max maxdist (distance (inc x) y)))

        (and (= inst "sw") (= parity 0))
        (recur (rest path) (dec x) (dec y) (max maxdist (distance (dec x) (dec y))))
        (= inst "sw")
        (recur (rest path) (dec x) y (max maxdist (distance (dec x) y)))))))

