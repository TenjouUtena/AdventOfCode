(ns aoc2019.machine
  (:require [clojure.string :as str]))

(defn op_TWO_RET_VAL [mem ip f]
  (let [o1 (mem (mem (+ ip 1)))
        o2 (mem (mem (+ ip 2)))
        rv (mem (+ ip 3))]
    [(assoc mem rv (f o1 o2)) (+ ip 4)]))

(defn op_PLUS [mem ip]
  (op_TWO_RET_VAL mem ip +))

(defn op_MUL [mem ip]
  (op_TWO_RET_VAL mem ip *))

(defn op-lookup [opcode]
  (case opcode
    1
    op_PLUS
    2
    op_MUL))

(defn run-machine [machine-spec noun verb & {:keys [trace] :or {trace false}}]
  (loop [[mem ip] [(assoc (assoc machine-spec 1 noun) 2 verb)
                   0]]
    (if trace (println (str "IP: " ip " - "
                            (subvec (apply conj mem (range 10)) ip (+ ip 7)))))
         (if (= 99 (mem ip))
           (mem 0)
           (recur ((op-lookup (mem ip)) mem ip))
           )))

(defn create-machine-spec [s]
  (vec (map #(Integer/parseInt %) (vec (str/split s #",")))))

