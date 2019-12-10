(ns aoc2019.machine
  (:require [clojure.string :as str]))

(defn op_PLUS [ms params]
  (let [{:keys [ip memory]} ms]
    (-> ms
        (assoc-in [:memory (nth params 5)] (+ (nth params 0) (nth params 1)))
        (assoc :ip (+ ip 4)))))

(defn op_MUL [ms params]
  (let [{:keys [ip memory]} ms]
    (-> ms
        (assoc-in [:memory (nth params 5)] (* (nth params 0) (nth params 1)))
        (assoc :ip (+ ip 4)))))

(defn op_INPUT [ms params]
  (if (empty? (:inputstream ms))
    (assoc ms :inputwait true)
    (let [{:keys [ip memory]} ms]
      (-> ms
          (assoc-in [:memory (nth params 1)] (first (:inputstream ms)))
          (assoc :inputstream (rest (:inputstream ms)))
          (assoc :ip (+ ip 2))))))

(defn op_OUTPUT [ms params]
  (-> ms
      (assoc :outputstream (conj (:outputstream ms) (nth params 0)))
      (assoc :ip (+ (:ip ms) 2))))

(defn op_NOOP [ms params]
  (println params)
  (assoc ms :ip (+ (:ip ms) 2)))

(defn op_JEZ [ms params]
  (assoc ms :ip
         (if (zero? (nth params 0))
           (nth params 1)
           (+ (:ip ms) 3))))

(defn op_JNZ [ms params]
  (assoc ms :ip
         (if (zero? (nth params 0))
           (+ (:ip ms) 3)
           (nth params 1))))

(defn op_EQ [ms params]
  (let [{:keys [ip memory]} ms
        out (if (= (nth params 0) (nth params 1)) 1 0)]
    (-> ms
        (assoc-in [:memory (nth params 5)] out)
        (assoc :ip (+ ip 4)))))

(defn op_LT [ms params]
  (let [{:keys [ip memory]} ms
        out (if (< (nth params 0) (nth params 1)) 1 0)]
    (-> ms
        (assoc-in [:memory (nth params 5)] out)
        (assoc :ip (+ ip 4)))))

(defn op_HALT [ms params]
  (assoc ms :halt true))

(defn op_REL_CHG [ms params]
  (-> ms
      (update :rel + (first params))
      (update :ip + 2)))

(defn op-lookup [opcode]
  (case opcode
    "01" op_PLUS
    "02" op_MUL
    "03" op_INPUT
    "04" op_OUTPUT ;Ouput
    "05" op_JNZ
    "06" op_JEZ
    "07" op_LT
    "08" op_EQ
    "09" op_REL_CHG
    "99" op_HALT

    op_NOOP))

(def op-len {"01" 3
             "02" 3
             "03" 1
             "04" 1
             "05" 2
             "06" 2
             "07" 3
             "08" 3
             "09" 1
             "99" 0})

(defn gather-param [indicator mem v ms]
  (case indicator
    \0 (mem v)
    \1 v
    \2 (mem (+ v (:rel ms)))))

(defn gather-param-output [indicator mem v ms]
  (case indicator
    \0 v
    \1 v
    \2 (+ (:rel ms) v)))

(defn decode-op [opcode mem ip machine-state]
  (let [fullop (str "00000000" opcode)
        op (apply str (take-last 2 fullop))
        params (concat (for [x (range 1 (inc (get op-len op)))]
                         (gather-param (last (drop-last (inc x) fullop)) mem (mem (+ ip x)) machine-state))
                       (for [x (range 1 (inc (get op-len op)))]
                         (gather-param-output (last (drop-last (inc x) fullop)) mem (mem (+ ip x)) machine-state)))]
    [op params]))

(def trace true)

(defn run-one-step [machine-state]
  (let [{:keys [ip memory]} machine-state
        [op params] (decode-op (memory ip) memory ip machine-state)]
    ((op-lookup op) machine-state params)))

(defn create-state-from-mem [mem]
  {:ip 0
   :histmem []
   :memory (vec (concat mem (take 1000 (repeat 0))))
   :halt false
   :rel 0
   :inputstream []
   :outputstream []
   :inputwait false})

(defn run-machine-base [state]
  (loop [ms state]
    (if (or (:halt ms) (:inputwait ms))
      ms
      (recur (run-one-step ms)))))

(defn run-machine
  ([machine-spec noun verb]
   (run-machine (assoc (assoc machine-spec 1 noun) 2 verb)))
  ([machine-spec]
   (run-machine-base (create-state-from-mem machine-spec))))

(defn run-machine-with-input [memory input]
  (run-machine-base (assoc (create-state-from-mem memory) :inputstream input)))

(defn create-machine-spec [s]
  (vec (map #(Integer/parseInt %) (vec (str/split (str/trim s) #",")))))

