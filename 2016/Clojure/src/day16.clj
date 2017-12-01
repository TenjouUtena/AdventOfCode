(ns day16)


(defn dragonpass [xs]
  (apply str xs "0" (map #(if (= % \1) \0 \1) (reverse xs))))

(defn dragon [len xs]
  (apply str (take len (first (drop-while #(>= len (count %)) (iterate dragonpass xs))))))

(defn xsumchar [a b]
  (if (= a b) "1" "0"))

(defn xsum [xs]
  (apply str (map #(apply xsumchar %) (partition 2 xs))))

(defn xsumall [xs]
  (first (drop-while #(even? (count %)) (iterate xsum xs))))

(defn thingy [len xs]
  (let [ dr (dragon len xs)
        ans (xsumall dr)
        ]ans))