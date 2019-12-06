(ns aoc2019.machine
  (:require [clojure.string :as str]))

(defn op_PLUS [mem ip params]
  [(assoc mem (mem (+ ip 3)) (+ (nth params 0) (nth params 1))) (+ ip 4)])

(defn op_MUL [mem ip params]
  [(assoc mem (mem (+ ip 3)) (* (nth params 0) (nth params 1))) (+ ip 4)])

;; THis is SUPSER A:LSFDK
(defn op_INPUT [mem ip params]
  [(assoc mem (mem (+ ip 1)) 5) (+ ip 2)])

(defn op_NOOP [mem ip params]
  (println params)
  [mem (+ ip 2)])

(defn op_JEZ [mem ip params]
  [mem (if (= 0 (nth params 0))
         (nth params 1)
         (+ ip 3))])

(defn op_JNZ [mem ip params]
  [mem (if (= 0 (nth params 0))
         (+ ip 3)
         (nth params 1))])

(defn op_EQ [mem ip params]
  (let [out (if (= (nth params 0) (nth params 1)) 1 0)]
    [(assoc mem (mem (+ ip 3)) out) (+ ip 4)]))

(defn op_LT [mem ip params]
  (let [out (if (< (nth params 0) (nth params 1)) 1 0)]
    [(assoc mem (mem (+ ip 3)) out) (+ ip 4)]))


(defn op-lookup [opcode]
  (case opcode
    "01" op_PLUS
    "02" op_MUL
    "03" op_INPUT
    "04" op_NOOP ;Ouput
    "05" op_JNZ
    "06" op_JEZ
    "07" op_LT
    "08" op_EQ

    op_NOOP))

(def op-len {
             "01" 3
             "02" 3
             "03" 1
             "04" 1
             "05" 2
             "06" 2
             "07" 3
             "08" 3
             "99" 0
             })

(defn gather-param [indicator mem v]
  (case indicator
    \0 (mem v)
    \1 v
      ))

(defn decode-op [opcode mem ip]
  (let [fullop (str "00000000" opcode)
        op (apply str (take-last 2 fullop))
        params (for [x (range 1 (inc (get op-len op)))]
                 (gather-param (last (drop-last (inc x) fullop)) mem (mem (+ ip x))))]
    (println fullop op params)
    [op params]
    ))

(def trace true)


(defn run-one-step [machine-state]
  (let [{:keys [ip memory]} machine-state
        [op params] (decode-op (memory ip) memory ip)
        [newmem newip] ((op-lookup op) memory ip params)]
    (-> machine-state
        (assoc :ip newip)
        (assoc :memory newmem)
        (assoc :halt (= op "99"))
        )
    ))


(defn create-state-from-mem [mem]
  {:ip 0
   :histmem []
   :memory mem
   :halt false})


(defn run-machine
  ([machine-spec noun verb]
  (run-machine (assoc (assoc machine-spec 1 noun) 2 verb)))
  ([machine-spec]
   (loop [[mem ip] [machine-spec 0]]
    (if trace (println (str "IP: " ip " - "
                            (subvec (apply conj mem (range 10)) ip (+ ip 7)))))
    (let [[op params] (decode-op (mem ip) mem ip)]
      (if (= "99" op)
        (mem 0)
        (recur ((op-lookup op) mem ip params)))))))

(defn create-machine-spec [s]
  (vec (map #(Integer/parseInt %) (vec (str/split (str/trim s) #",")))))

