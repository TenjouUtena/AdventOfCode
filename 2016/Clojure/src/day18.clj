 (ns day18)
 (def seedrow ".^^^.^.^^^.^.......^^.^^^^.^^^^..^^^^^.^.^^^..^^.^.^^..^.^..^^...^.^^.^^^...^^.^.^^^..^^^^.....^....")
 (def rows 400000)
 (def cols (count seedrow))

 (declare wall?)
 (defn wall_base? [x y]
   (let [prevrow (dec y) prevcol (dec x) nextcol (inc x)]
     (cond
       (or (< x 0) (< y 0) (>= x cols)) false
       (= y 0) (= (nth seedrow x) \^)
       (and (wall? prevcol prevrow) (not (wall? nextcol prevrow))) true
       (and (not (wall? prevcol prevrow)) (wall? nextcol prevrow)) true
       :else false)))
 (def wall? (memoize wall_base?))

 (defn countrow [row]
  (count (filter true? (pmap #(wall? % row) (range cols)))))
 (defn countall []
   (reduce + (pmap countrow (range rows))))
 (defn countspace []
   (- (* rows cols) (countall)))

 (defn wallind [x y]
   (if (wall? x y) \^ \.))
 (defn printwalls []
   (doseq [y (range rows)]
     (println (apply str (map #(wallind % y) (range cols))))))