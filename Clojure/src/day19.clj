 (ns day19)


 (defn findelfpt1 [numelfs]
   (loop [elfs (range numelfs)]
     (cond
       (= (count elfs) 1) (+ 1 (first elfs))
       (= (mod (count elfs) 2) 1) (recur (rest (take-nth 2 elfs)))
       :else (recur (take-nth 2 elfs)))))

;; Part 2 way less clever - Doesn't work it's probably off by 1 somewhere.
 (defn findelfpt2 [numelfs]
   (loop [elfs (vec (range numelfs))
          turn 0]
     (let [badelf (mod (+ (int (+ (/ (count elfs) 2) 1)) (- turn 1)) (count elfs))
           f (subvec elfs 0 badelf)
           l (subvec elfs (+ badelf 1))]
       ;;       (println elfs turn)
       (cond (= (count elfs) 1) (+ (first elfs) 1)
             :else
             (recur (vec (concat f l))
                    (if (>= turn (- (count elfs) 0)) 0 (+ turn 1)))))))
